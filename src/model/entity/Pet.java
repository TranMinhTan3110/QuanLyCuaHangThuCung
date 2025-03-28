package model.entity;

public class Pet {
    private int petID;
    private String name;
    private String species;// loại thú cưng(chó mèo)
    private String breed;//giống loài
    private int age;
    private double price;

    public Pet(int petID, String name, String breed, String species, int age, double price) {
        this.petID = petID;
        this.name = name;
        this.breed = breed;
        this.species = species;
        this.age = age;
        this.price = price;
    }
    public Pet(){

    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
