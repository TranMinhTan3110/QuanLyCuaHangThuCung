package respository.dao;

import model.entity.Customer;
import respository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements DaoInterface<Customer> {

	@Override
	public boolean insert(Customer customer) {
		String sqlPerson = "INSERT INTO Person(name, phone, address) VALUES(?, ?, ?)";
		String sqlCustomer = "INSERT INTO Customer(id, loyaltyPoints, membershipLevel, trangThai) VALUES (?, ?, ?, ?)";

		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement stPerson = con.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
			 PreparedStatement stCustomer = con.prepareStatement(sqlCustomer)) {

			con.setAutoCommit(false);

			stPerson.setString(1, customer.getName());
			stPerson.setString(2, customer.getPhone());
			stPerson.setString(3, customer.getAddress());
			stPerson.executeUpdate();

			ResultSet resultset = stPerson.getGeneratedKeys();
			int personId;
			if (resultset.next()) {
				personId = resultset.getInt(1);
				customer.setId(personId);
			} else {
				throw new SQLException("Cannot get Person ID!");
			}

			stCustomer.setInt(1, personId);
			stCustomer.setInt(2, customer.getLoyaltyPoints());
			stCustomer.setString(3, customer.getMembershipLevel());
			stCustomer.setString(4, "hoạt động");
			stCustomer.executeUpdate();

			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Customer customer) {
		String sqlPerson = "UPDATE Person SET name = ?, phone = ?, address = ? WHERE id = ?";
		String sqlCustomer = "UPDATE Customer SET loyaltyPoints = ?, membershipLevel = ?, trangThai = ? WHERE id = ?";

		try (Connection con = DatabaseConnection.getConnection()) {
			try (PreparedStatement st1 = con.prepareStatement(sqlPerson)) {
				st1.setString(1, customer.getName());
				st1.setString(2, customer.getPhone());
				st1.setString(3, customer.getAddress());
				st1.setInt(4, customer.getId());
				st1.executeUpdate();
			}

			try (PreparedStatement st2 = con.prepareStatement(sqlCustomer)) {
				st2.setInt(1, customer.getLoyaltyPoints());
				st2.setString(2, customer.getMembershipLevel());
				st2.setString(3, customer.getStatus() == null ? "hoạt động" : customer.getStatus());
				st2.setInt(4, customer.getId());
				st2.executeUpdate();
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Soft delete: set trangThai to "ngưng hoạt động"
	@Override
	public boolean delete(Customer customer) {
		String sql = "UPDATE Customer SET trangThai = ? WHERE id = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "ngưng hoạt động");
			st.setInt(2, customer.getId());
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Customer> getAll() {
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE c.trangThai = ?";
		ArrayList<Customer> customers = new ArrayList<>();
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "hoạt động");
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String address = rs.getString("address");
					int loyaltyPoints = rs.getInt("loyaltyPoints");
					String membershipLevel = rs.getString("membershipLevel");
					String status = rs.getString("trangThai");

					Customer customer = new Customer(id, name, phone, address, loyaltyPoints, membershipLevel, status);
					customers.add(customer);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer selectByID(int id) {
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE p.id = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String address = rs.getString("address");
					int loyaltyPoints = rs.getInt("loyaltyPoints");
					String membershipLevel = rs.getString("membershipLevel");
					String status = rs.getString("trangThai");
					return new Customer(id, name, phone, address, loyaltyPoints, membershipLevel, status);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean findByPhone(String phone) {
		String sql = "SELECT 1 FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE p.phone = ? AND c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, phone);
			st.setString(2, "hoạt động");
			try (ResultSet rs = st.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Customer> customerListByName(String name) {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Customer c JOIN Person p ON c.id = p.id " +
				"WHERE LOWER(p.name) LIKE ? AND c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "%" + name.toLowerCase().trim() + "%");
			st.setString(2, "hoạt động");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("loyaltyPoints"),
						rs.getString("membershipLevel"),
						rs.getString("trangThai")
				);
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public List<Customer> customerListByPhone(String phone) {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Customer c JOIN Person p ON c.id = p.id " +
				"WHERE LOWER(p.phone) LIKE ? AND c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, phone.toLowerCase().trim() + "%");
			st.setString(2, "hoạt động");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("loyaltyPoints"),
						rs.getString("membershipLevel"),
						rs.getString("trangThai")
				);
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public List<Customer> getByStatus(String status) {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				customers.add(new Customer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("loyaltyPoints"),
						rs.getString("membershipLevel"),
						rs.getString("trangThai")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public List<Customer> customerListByNameAndStatus(String name, String status) {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Customer c JOIN Person p ON c.id = p.id " +
				"WHERE LOWER(p.name) LIKE ? AND c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "%" + name.toLowerCase().trim() + "%");
			st.setString(2, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				customers.add(new Customer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("loyaltyPoints"),
						rs.getString("membershipLevel"),
						rs.getString("trangThai")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public List<Customer> customerListByPhoneAndStatus(String phone, String status) {
		List<Customer> customers = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel, c.trangThai " +
				"FROM Customer c JOIN Person p ON c.id = p.id " +
				"WHERE LOWER(p.phone) LIKE ? AND c.trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, phone.toLowerCase().trim() + "%");
			st.setString(2, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				customers.add(new Customer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("address"),
						rs.getInt("loyaltyPoints"),
						rs.getString("membershipLevel"),
						rs.getString("trangThai")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

}