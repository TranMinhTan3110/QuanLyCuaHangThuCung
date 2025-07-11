package model.entity;

public class Pet {
	private int petID;
	private String name;
	private String species;// loại thú cưng(chó mèo)
	private String breed;// giống loài
	private float age;
	private double price;
	private String gender;
	private String trangThai; // thêm trạng thái

	public Pet(int petID, String name, String breed, String species, float age, double price, String gender) {
		this.petID = petID;
		this.name = name;
		this.breed = breed;
		this.species = species;
		this.age = age;
		this.price = price;
		this.gender = gender;
	}

	// getter, setter cho tất cả thuộc tính
	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Pet() {

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

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
