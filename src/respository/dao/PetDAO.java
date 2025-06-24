package respository.dao;

import model.entity.Pet;
import respository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetDAO implements DaoInterface<Pet> {
	@Override
	public boolean insert(Pet pet) {
		String sql = "INSERT INTO Pet(name, species, breed, age, price, gender) VALUES(?,?,?,?,?,?)";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, pet.getName());
			st.setString(2, pet.getSpecies());
			st.setString(3, pet.getBreed());
			st.setFloat(4, pet.getAge());
			st.setDouble(5, pet.getPrice());
			st.setString(6, pet.getGender());
			int check = st.executeUpdate();
			return check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Pet pet) {
		String sql = "UPDATE Pet SET name = ?, species = ?, breed = ?, age = ?, price = ?, gender = ? WHERE petID = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, pet.getName());
			st.setString(2, pet.getSpecies());
			st.setString(3, pet.getBreed());
			st.setFloat(4, pet.getAge());
			st.setDouble(5, pet.getPrice());
			st.setString(6, pet.getGender());
			st.setInt(7, pet.getPetID()); // thêm dòng này

			int check = st.executeUpdate();
			return check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Pet pet) {
		if (pet == null)
			return false;

		String sql = "DELETE FROM Pet WHERE petID = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, pet.getPetID());

			int check = st.executeUpdate(); // KHÔNG truyền sql vào đây
			return check > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Pet> getAll() {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet WHERE trangThai = N'chưa bán'";

		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"),
						rs.getString("breed"), rs.getInt("age"), rs.getDouble("price"), rs.getString("gender"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}

	@Override
	public Pet selectByID(int id) {
		String sql = "SELECT * FROM Pet WHERE petID = ?";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, id);
			ResultSet rs = st.executeQuery(); // KHÔNG truyền sql vào đây

			if (rs.next()) {
				return new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"), rs.getString("breed"),
						rs.getInt("age"), rs.getDouble("price"), rs.getString("gender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Pet> searchByName(String keyword, String status) {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet WHERE name LIKE ? AND trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, "%" + keyword + "%");
			st.setString(2, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("breed"),
						rs.getString("species"), rs.getFloat("age"), rs.getDouble("price"), rs.getString("gender"));
				pet.setTrangThai(rs.getString("trangThai"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}

	public ArrayList<Pet> filterBySpecies(String species, String status) {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet WHERE species = ? AND trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, species);
			st.setString(2, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"),
						rs.getString("breed"), rs.getInt("age"), rs.getDouble("price"), rs.getString("gender"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}

	public ArrayList<String> getAllBreeds() {
		ArrayList<String> breeds = new ArrayList<>();
		String sql = "SELECT DISTINCT breed FROM Pet WHERE (trangThai = N'chưa bán' OR trangThai IS NULL) ORDER BY breed";

		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql);
			 ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				breeds.add(rs.getString("breed"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return breeds;
	}

	public ArrayList<String> getAllSpecies() {
		ArrayList<String> speciesList = new ArrayList<>();
		String sql = "SELECT DISTINCT species FROM Pet WHERE (trangThai = N'chưa bán' OR trangThai IS NULL) ORDER BY species";

		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql);
			 ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				speciesList.add(rs.getString("species"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return speciesList;
	}

	public ArrayList<Pet> filterBySpeciesAndBreed(String species, String breed, String priceOrder, String status) {
		ArrayList<Pet> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Pet WHERE species = ? AND breed = ? AND trangThai = ?");
		if (priceOrder != null && !priceOrder.isEmpty()) {
			sql.append(" ORDER BY price ").append(priceOrder);
		}
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql.toString())) {
			ps.setString(1, species);
			ps.setString(2, breed);
			ps.setString(3, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("breed"),
						rs.getString("species"), rs.getFloat("age"), rs.getDouble("price"), rs.getString("gender"));
				pet.setTrangThai(rs.getString("trangThai"));
				list.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Pet> filterByBreed(String breed, String status) {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet WHERE breed = ? AND trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, breed);
			st.setString(2, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("breed"),
						rs.getString("species"), rs.getFloat("age"), rs.getDouble("price"), rs.getString("gender"));
				pet.setTrangThai(rs.getString("trangThai"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}

	public ArrayList<Pet> sortByPrice(boolean ascending) {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet ORDER BY price " + (ascending ? "ASC" : "DESC");

		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"),
						rs.getString("breed"), rs.getInt("age"), rs.getDouble("price"), rs.getString("gender"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}

	public ArrayList<Pet> sortByBreed() {
		ArrayList<Pet> list = new ArrayList<>();
		String sql = "SELECT * FROM Pet ORDER BY breed"; // 'đực', 'cái'
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				list.add(new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"),
						rs.getString("breed"), rs.getInt("age"), rs.getDouble("price"), rs.getString("gender")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<Pet> filterAndSort(String species, String priceOrder, String status) {
		ArrayList<Pet> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Pet WHERE trangThai = ?");
		if (species != null && !species.isEmpty()) {
			sql.append(" AND species = ?");
		}
		if (priceOrder != null && !priceOrder.isEmpty()) {
			sql.append(" ORDER BY price ").append(priceOrder);
		}
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement ps = con.prepareStatement(sql.toString())) {
			int paramIndex = 1;
			ps.setString(paramIndex++, status);
			if (species != null && !species.isEmpty()) {
				ps.setString(paramIndex++, species);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("breed"),
						rs.getString("species"), rs.getFloat("age"), rs.getDouble("price"), rs.getString("gender"));
				pet.setTrangThai(rs.getString("trangThai"));
				list.add(pet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getPetNameById(int petId) {
		String sql = "SELECT name FROM Pet WHERE petID = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, petId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Unknown Pet";
	}

	public boolean deletePetByID(int petID) {
		String sql = "DELETE FROM Pet WHERE petID = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, petID);
			int affectedRows = stmt.executeUpdate();

			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePetStatus(int petID, String status) {
		String sql = "UPDATE Pet SET trangThai = ? WHERE petID = ?";
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, status);
			ps.setInt(2, petID);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isPetExists(String name) {
		String sql = "SELECT COUNT(*) FROM Pet WHERE name = ? AND (trangThai = N'chưa bán' OR trangThai IS NULL)";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, name);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Pet> getPetsByStatus(String status) {
		ArrayList<Pet> pets = new ArrayList<>();
		String sql = "SELECT * FROM Pet WHERE trangThai = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {
			st.setString(1, status);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Pet pet = new Pet(rs.getInt("petID"), rs.getString("name"), rs.getString("species"),
						rs.getString("breed"), rs.getInt("age"), rs.getDouble("price"), rs.getString("gender"));
				pet.setTrangThai(rs.getString("trangThai"));
				pets.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pets;
	}
}
