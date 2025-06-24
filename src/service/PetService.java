package service;

import respository.dao.PetDAO;
import model.entity.Pet;

import java.util.ArrayList;

public class PetService {
    private PetDAO petDAO;

    // Constructor truyền vào PetDAO (không khởi tạo mới bên trong)
    public PetService(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    // Lấy toàn bộ Pet
    public ArrayList<Pet> getAll() {
        return petDAO.getAll();
    }

    // Thêm Pet
    public boolean insert(Pet pet) {
        return petDAO.insert(pet);
    }

    // Cập nhật Pet
    public boolean update(Pet pet) {
        return petDAO.update(pet);
    }

    // Xóa Pet
    public boolean delete(Pet pet) {
        return petDAO.delete(pet);
    }

    // Tìm Pet theo ID
    public Pet selectByID(int petID) {
        return petDAO.selectByID(petID);
    }

    // Tìm gần đúng theo tên
    public ArrayList<Pet> searchByNameLike(String name, String status) {
        return petDAO.searchByName(name, status);
    }

    // Lọc theo Species
    public ArrayList<Pet> filterBySpecies(String species, String status) {
        return petDAO.filterBySpecies(species, status);
    }
    public ArrayList<String> getAllBreeds() {
        return petDAO.getAllBreeds();
    }

    public ArrayList<Pet> filterByBreed(String breed, String status) {
        return petDAO.filterByBreed(breed, status);
    }

    // Sắp xếp theo giá tăng dần
    public ArrayList<Pet> sortByPriceAsc() {
        return petDAO.sortByPrice(true);
    }

    // Sắp xếp theo giá giảm dần
    public ArrayList<Pet> sortByPriceDesc() {
        return petDAO.sortByPrice(false);
    }

    // Sắp xếp giá tuỳ chọn
    public ArrayList<Pet> sortByPrice(boolean ascending) {
        return petDAO.sortByPrice(ascending);
    }

    // Lọc và sắp xếp theo species + giá
    public ArrayList<Pet> filterALL(String species, String priceOrder, String status) {
        return petDAO.filterAndSort(species, priceOrder, status);
    }

    public ArrayList<Pet> filterBySpeciesAndBreed(String species, String breed, String priceOrder, String status) {
        return petDAO.filterBySpeciesAndBreed(species, breed, priceOrder, status);
    }

    public ArrayList<String> getAllSpecies() {
        return petDAO.getAllSpecies();
    }

    // Kiểm tra tên Pet đã tồn tại chưa
    public boolean isProductExist(String name) {
        return petDAO.isPetExists(name);
    }

    // Lấy tên Pet theo ID
    public String getPetName(int id) {
        return petDAO.getPetNameById(id);
    }

    // Bán pet (xoá theo ID)
    public boolean sellPet(int id) {
        return petDAO.deletePetByID(id);
    }

    public ArrayList<Pet> getPetsByStatus(String status) {
        return petDAO.getPetsByStatus(status);
    }

    // Cập nhật trạng thái
    public boolean updateTrangThai(int petID, String status) {
        return petDAO.updatePetStatus(petID, status);
    }
}
