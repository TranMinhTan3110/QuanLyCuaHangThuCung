// src/controller/PetController.java
package controller;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.entity.Pet;
import service.PetService;
import view.PetView;

public class PetController {
	private PetView view;
	private PetService service;

	// Pagination fields
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalPage = 1;
	private List<Pet> currentPetList = null;

	// Track current status for filtering
	private String currentStatus = "chưa bán";

	public PetController(PetView view, PetService service) {
		this.view = view;
		this.service = service;
		initController();
		addEventListeners();
		loadTableData();
		filterPets();
	}

	private void initController() {
		view.getPetTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && view.getPetTable().getSelectedRow() != -1) {
				loadSelectedPetIntoForm();
			}
		});

		updateBreedComboBox();
		updateSpeciesComboBox();
	}

	private void loadSelectedPetIntoForm() {
		int selectedRow = view.getPetTable().getSelectedRow();
		if (selectedRow != -1) {
			String idStr = view.getPetTable().getValueAt(selectedRow, 0).toString();
			String name = view.getPetTable().getValueAt(selectedRow, 1).toString();
			String breed = view.getPetTable().getValueAt(selectedRow, 2).toString();
			String species = view.getPetTable().getValueAt(selectedRow, 3).toString();
			String gender = view.getPetTable().getValueAt(selectedRow, 4).toString();
			String age = view.getPetTable().getValueAt(selectedRow, 5).toString();
			String price = view.getPetTable().getValueAt(selectedRow, 6).toString();
			view.setPetData(idStr, name, breed, species, gender, age, price);
		}
	}

	private void addEventListeners() {
		view.getAddButton().addActionListener(e -> addPet());
		view.getDeleteButton().addActionListener(e -> deletePet());
		view.getEditButton().addActionListener(e -> editPet());
		view.addSearchKeyListener(new SearchListener());
		view.getArrangeComboBox().addActionListener(e -> filterPets());
		view.getSpeciesComboBox().addActionListener(e -> filterPets());
		view.getBreedComboBox().addActionListener(e -> filterPets());

		// Pagination button listeners
		view.getPrevPageButton().addActionListener(e -> {
			if (currentPage > 1) {
				currentPage--;
				updateTablePage();
			}
		});
		view.getNextPageButton().addActionListener(e -> {
			if (currentPage < totalPage) {
				currentPage++;
				updateTablePage();
			}
		});

		// ComboBox listeners for text fields
		view.getBreedComboBox().addActionListener(e -> {
			Object selected = view.getBreedComboBox().getSelectedItem();
			if (selected != null && !selected.toString().equals("Tất cả")) {
				view.setBreedText(selected.toString());
			}
		});
		view.getSpeciesComboBox().addActionListener(e -> {
			Object selected = view.getSpeciesComboBox().getSelectedItem();
			if (selected != null && !selected.toString().equals("Tất cả")) {
				view.setSpeciesText(selected.toString());
			}
		});

		// Status tab buttons
		view.getShowStoppedButton().addActionListener(e -> {
			currentStatus = "ngừng kinh doanh";
			filterPets();
			view.getAddButton().setEnabled(false);
			view.getDeleteButton().setEnabled(false);
			view.getEditButton().setEnabled(true); // Allow edit, but confirm on edit
		});
		view.getShowSoldButton().addActionListener(e -> {
			currentStatus = "đã bán";
			filterPets();
			view.getAddButton().setEnabled(false);
			view.getDeleteButton().setEnabled(false);
			view.getEditButton().setEnabled(false); // Disable edit
		});
		view.getShowAvailableButton().addActionListener(e -> {
			currentStatus = "chưa bán";
			filterPets();
			view.getAddButton().setEnabled(true);
			view.getDeleteButton().setEnabled(true);
			view.getEditButton().setEnabled(true); // Enable edit
		});
	}

	class SearchListener implements KeyListener {
		@Override
		public void keyReleased(KeyEvent e) {
			String keyword = view.getSearchKeyword();
			currentPetList = service.searchByNameLike(keyword, currentStatus);
			currentPage = 1;
			updateTablePage();
		}
		@Override public void keyTyped(KeyEvent e) {}
		@Override public void keyPressed(KeyEvent e) {}
	}

	private void loadTableData() {
		currentPetList = service.getPetsByStatus(currentStatus);
		currentPage = 1;
		updateTablePage();
	}

	private void updateBreedComboBox() {
		ArrayList<String> breeds = service.getAllBreeds();
		view.getBreedComboBox().removeAllItems();
		view.getBreedComboBox().addItem("Tất cả");
		for (String breed : breeds) {
			view.getBreedComboBox().addItem(breed);
		}
	}

	private void updateSpeciesComboBox() {
		ArrayList<String> speciesList = service.getAllSpecies();
		view.getSpeciesComboBox().removeAllItems();
		view.getSpeciesComboBox().addItem("Tất cả");
		for (String species : speciesList) {
			view.getSpeciesComboBox().addItem(species);
		}
	}

	private void addPet() {
		String name = view.getNameTextField();
		String breed = view.getBreed_textField();
		String priceStr = view.getPriceTextField();
		String species = view.getSpeciesTextField();
		String gender = view.getGenderComboBox().getSelectedItem().toString();
		String ageStr = view.getAgeTextField();

		if (name.isEmpty() || name.equals("Enter Name") || species.isEmpty() || species.equals("Enter species")
				|| breed.isEmpty() || breed.equals("Enter breed") || ageStr.isEmpty() || ageStr.equals("Enter Age")
				|| priceStr.isEmpty() || priceStr.equals("Enter Price")) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
			return;
		}
		if (service.isProductExist(name)) {
			JOptionPane.showMessageDialog(view, "Tên pet này đã tồn tại!");
			return;
		}

		try {
			float age = Float.parseFloat(view.getAgeTextField().replace(",", "."));
			double price = Double.parseDouble(view.getPriceTextField().replace(",", "."));
			if (age <= 0) {
				JOptionPane.showMessageDialog(view, "Tuổi phải là số dương!");
				return;
			}
			Pet pet = new Pet();
			pet.setName(name);
			pet.setSpecies(species);
			pet.setBreed(breed);
			pet.setAge(age);
			pet.setPrice(price);
			pet.setGender(gender);
			service.insert(pet);
			loadTableData();
			updateBreedComboBox();
			updateSpeciesComboBox();
			view.clearFields();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho Giá và Tuổi!");
		}
	}

	private boolean containsNumber(String text) {
		for (char c : text.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}

	private void editPet() {
		int selectedRow = view.getPetTable().getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn pet để chỉnh sửa.");
			return;
		}
		// Confirm if editing in "ngừng kinh doanh"
		if ("ngừng kinh doanh".equalsIgnoreCase(currentStatus)) {
			int confirm = JOptionPane.showConfirmDialog(view,
					"Bạn có chắc chắn muốn chỉnh sửa và đưa pet này về trạng thái 'đang kinh doanh'?",
					"Xác nhận chỉnh sửa", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) {
				return;
			}
		}
		String name = view.getNameTextField();
		String species = view.getSpeciesTextField();
		String breed = view.getBreed_textField();
		String ageStr = view.getAgeTextField();
		String priceStr = view.getPriceTextField();
		String gender = view.getGenderComboBox().getSelectedItem().toString();
		int petID = Integer.parseInt(view.getPetTable().getValueAt(selectedRow, 0).toString());
		if (name.isEmpty() || name.equals("Enter Name") || species.isEmpty() || species.equals("Enter species") ||
				breed.isEmpty() || breed.equals("Enter breed") || ageStr.isEmpty() || ageStr.equals("Enter Age") ||
				priceStr.isEmpty() || priceStr.equals("Enter Price")) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
			return;
		}
		try {
			float age = Float.parseFloat(view.getAgeTextField().replace(",", "."));
			double price = Double.parseDouble(view.getPriceTextField().replace(",", "."));
			if (containsNumber(name) || containsNumber(breed) || containsNumber(species)) {
				JOptionPane.showMessageDialog(view, "Không được nhập số cho Tên, Giống và Loài!");
				return;
			}
			Pet pet = new Pet(petID, name, breed, species, age, price, gender);
			service.update(pet);
			// If editing in "ngừng kinh doanh", set status back to "chưa bán"
			if ("ngừng kinh doanh".equalsIgnoreCase(currentStatus)) {
				service.updateTrangThai(petID, "chưa bán");
			}
			loadTableData();
			updateBreedComboBox();
			updateSpeciesComboBox();
			view.clearFields();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho Tuổi và Giá!");
		}
	}

	private void deletePet() {
		int row = view.getPetTable().getSelectedRow();
		if (row >= 0) {
			int confirm = JOptionPane.showConfirmDialog(view,
					"Bạn có chắc chắn muốn ngừng kinh doanh pet này?",
					"Xác nhận", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				int id = Integer.parseInt(view.getPetTable().getValueAt(row, 0).toString());
				service.updateTrangThai(id, "ngừng kinh doanh");
				loadTableData();
				view.clearFields();
			}
		} else {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn pet để ngừng kinh doanh.");
		}
	}

	private void filterPets() {
		Object speciesObj = view.getSpeciesComboBox().getSelectedItem();
		Object breedObj = view.getBreedComboBox().getSelectedItem();
		Object arrangeObj = view.getArrangeComboBox().getSelectedItem();

		String species = (speciesObj != null) ? speciesObj.toString() : "Tất cả";
		String breed = (breedObj != null) ? breedObj.toString() : "Tất cả";
		String selectedArrange = (arrangeObj != null) ? arrangeObj.toString() : "Tăng dần";
		String sqlPriceOrder = null;

		if (selectedArrange.equals("Tăng dần")) {
			sqlPriceOrder = "ASC";
		} else if (selectedArrange.equals("Giảm dần")) {
			sqlPriceOrder = "DESC";
		}

		// Filtering logic with status
		if (species.equals("Tất cả") && breed.equals("Tất cả")) {
			currentPetList = service.filterALL(null, sqlPriceOrder, currentStatus);
		} else if (!species.equals("Tất cả") && breed.equals("Tất cả")) {
			currentPetList = service.filterALL(species, sqlPriceOrder, currentStatus);
		} else if (species.equals("Tất cả") && !breed.equals("Tất cả")) {
			currentPetList = service.filterByBreed(breed, currentStatus);
		} else {
			currentPetList = service.filterBySpeciesAndBreed(species, breed, sqlPriceOrder, currentStatus);
		}

		currentPage = 1;
		updateTablePage();
	}

	private void updateTablePage() {
		if (currentPetList == null) currentPetList = service.getPetsByStatus(currentStatus);
		int total = currentPetList.size();
		totalPage = (int) Math.ceil((double) total / pageSize);
		if (totalPage == 0) totalPage = 1;
		if (currentPage > totalPage) currentPage = totalPage;
		if (currentPage < 1) currentPage = 1;
		int start = (currentPage - 1) * pageSize;
		int end = Math.min(start + pageSize, total);

		DefaultTableModel model = (DefaultTableModel) view.getPetTable().getModel();
		model.setRowCount(0);
		for (int i = start; i < end; i++) {
			Pet pet = currentPetList.get(i);
			model.addRow(new Object[]{
					pet.getPetID(),
					pet.getName(),
					pet.getBreed(),
					pet.getSpecies(),
					pet.getGender(),
					pet.getAge(),
					pet.getPrice()
			});
		}
		view.getPageInfoLabel().setText("Page " + currentPage + "/" + (totalPage == 0 ? 1 : totalPage));
		view.getPrevPageButton().setEnabled(currentPage > 1);
		view.getNextPageButton().setEnabled(currentPage < totalPage);
	}
}