package model.entity;

public class Category {
	private int categoryID;
	private String categoryName;

	public Category(int categoryID, String categoryName) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;

	}

	public int getCategoryID() {
		return categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return categoryName;
	}

	public String getName() {
		return categoryName;
	}
}
