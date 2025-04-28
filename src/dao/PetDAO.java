package dao;

import model.entity.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PetDAO implements DaoInterface<Pet>{
    @Override
    public boolean insert(Pet pet) {
        String sql = "INSERT INTO Pet(name, species, breed, age, price) VALUES(?,?,?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, pet.getName());
            st.setString(2, pet.getSpecies());
            st.setString(3, pet.getBreed());
            st.setFloat(4, pet.getAge());
            st.setDouble(5, pet.getPrice());

            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Pet pet) {
        String sql = "UPDATE Pet SET name = ?, species = ?, breed = ?, age = ?, price = ? WHERE petID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, pet.getName());
            st.setString(2, pet.getSpecies());
            st.setString(3, pet.getBreed());
            st.setFloat(4, pet.getAge());
            st.setDouble(5, pet.getPrice());
            st.setInt(6, pet.getPetID()); // thêm dòng này

            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Pet pet) {
        if (pet == null) return false;

        String sql = "DELETE FROM Pet WHERE petID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

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
        String sql = "SELECT * FROM Pet";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) { // KHÔNG truyền sql vào đây

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
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

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery(); // KHÔNG truyền sql vào đây

            if (rs.next()) {
                return new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pet selectByName(String name) {
        // Viết luôn cho bạn
        String sql = "SELECT * FROM Pet WHERE name = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, name);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pet> searchByName(String keyword) {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet WHERE name LIKE ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public ArrayList<Pet> filterBySpecies(String species) {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet WHERE species = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, species);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
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
                Pet pet = new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price")
                );
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }


}
