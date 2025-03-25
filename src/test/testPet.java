package test;

import dao.PetDAO;
import model.entity.Pet;

public class testPet {
    public static void main(String[] args) {
        Pet p = new Pet(1,"husky","cho","duc",5,500000);
        PetDAO k = new PetDAO();
        k.insert(p);

    }
}
