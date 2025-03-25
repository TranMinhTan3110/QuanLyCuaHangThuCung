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
            st.setInt(4, pet.getAge());
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
        String sql = "UPDATE Pet SET  name = ?, species = ?, breed = ?, age = ?, price = ? where id = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement st = con.prepareStatement(sql);)
        {
            st.setString(1,pet.getName());
            st.setString(2,pet.getSpecies());
            st.setString(3,pet.getBreed());
            st.setInt(4,pet.getAge());
            st.setDouble(5,pet.getPrice());

            int check = st.executeUpdate();
            return check > 0;
        }catch (SQLException e) {
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

            int check = st.executeUpdate(sql);
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
             ResultSet rs = st.executeQuery(sql)) {

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
            ResultSet rs = st.executeQuery(sql);

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

}
