package service;

import dao.DaoInterface;
import dao.PetDAO;
import model.entity.Pet;

import java.util.ArrayList;

public class PetService {
    private DaoInterface daoPet;
    private PetDAO petDAO;
    // Constructor truyền vào Repository
    public PetService(DaoInterface petRepo) {
        this.daoPet = petRepo;
        this.petDAO = new PetDAO();
    }

    // Lấy toàn bộ Pet
    public ArrayList<Pet> getAll() {
        return daoPet.getAll();
    }

    // Thêm Pet
    public boolean insert(Pet pet) {
        return daoPet.insert(pet);
    }

    // Cập nhật Pet
    public boolean update(Pet pet) {
        return daoPet.update(pet);
    }

    // Xóa Pet
    public boolean delete(Pet pet) {
        return daoPet.delete(pet);
    }

    // Tìm Pet theo ID
    public Pet selectByID(int petID) {
        return (Pet) daoPet.selectByID(petID);
    }

    // Tìm gần đúng theo tên
    public ArrayList<Pet> searchByNameLike(String name) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).searchByName(name);
        }
        return new ArrayList<>();
    }

    // Lọc theo Species
    public ArrayList<Pet> filterBySpecies(String species) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).filterBySpecies(species);
        }
        return new ArrayList<>();
    }

    // Sắp xếp theo giá tăng dần
    public ArrayList<Pet> sortByPriceAsc() {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).sortByPrice(true);
        }
        return new ArrayList<>();
    }

    // Sắp xếp theo giá giảm dần
    public ArrayList<Pet> sortByPriceDesc() {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).sortByPrice(false);
        }
        return new ArrayList<>();
    }
    public ArrayList<Pet> sortByPrice(boolean check) {
        return petDAO.sortByPrice(check);

    }


    public ArrayList<Pet> filterALL(String species, String priceOrder) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).filterAndSort(species,priceOrder);
        }
        return new  ArrayList<>();
    }

    //kiểm tra pet đã tồn tại chưa
    public boolean isProductExist(String name){
        return  petDAO.isPetExists(name);
    }
    public String getPetName(int id) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).getPetNameById(id);
        }
        return null;
    }

    public boolean sellPet(int id) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).deletePetByID(id);
        }
        return false;
    }

    public boolean updateTrangThai(int petID, String status) {
        if (daoPet instanceof PetDAO) {
            return ((PetDAO) daoPet).updatePetStatus(petID, status);
        }
        return false;
    }
}