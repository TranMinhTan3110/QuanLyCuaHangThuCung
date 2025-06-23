package controller;

import java.awt.event.*;
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

	public PetController(PetView view, PetService service) {
		this.view = view;
		this.service = service;
		initController();
		addEventListeners();
		loadTableData();
//		filterPets();
	}

	private void initController() {
		view.getPetTable().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && view.getPetTable().getSelectedRow() != -1) {
				loadSelectedPetIntoForm();
			}
		});
	}

	public void loadSelectedPetIntoForm(){
		int selectedRow = view.getPetTable().getSelectedRow();
		if(selectedRow != -1){
			String idStr = view.getPetTable().getValueAt(selectedRow,0).toString();
			String name = view.getPetTable().getValueAt(selectedRow,1).toString();
			String breed = view.getPetTable().getValueAt(selectedRow,2).toString();
			String price = view.getPetTable().getValueAt(selectedRow,3).toString();
			String species = view.getPetTable().getValueAt(selectedRow,4).toString();
			String comboBox = view.getPetTable().getValueAt(selectedRow,5).toString();
			String age = view.getPetTable().getValueAt(selectedRow,6).toString();
			view.setPetData(idStr, name, species, price, breed,comboBox, age);
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
	}

	class SearchListener implements KeyListener {
		@Override
		public void keyReleased(KeyEvent e) {
			String keyword = view.getSearchKeyword();
			currentPetList = service.searchByNameLike(keyword);
			currentPage = 1;
			updateTablePage();
		}
		@Override public void keyTyped(KeyEvent e) {}
		@Override public void keyPressed(KeyEvent e) {}
	}

	private void loadTableData() {
		currentPetList = service.getAll();
		currentPage = 1;
		updateTablePage();
	}

	private void addPet() {
		String name = view.getNameTextField();
		String breed = view.getBreed_textField();
		String priceStr = view.getPriceTextField();
		String species = view.getSpeciesTextField();
		String gender = view.getGenderComboBox().getSelectedItem().toString();
		String ageStr = view.getAgeTextField();

		if (name.isEmpty() ||name.equals("Enter Name") || species.isEmpty() ||species.equals("Enter species") || breed.isEmpty() || breed.equals("Enter breed") || ageStr.isEmpty()|| ageStr.equals("Enter Age") || priceStr.isEmpty() ||priceStr.equals("Enter Price")) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
			return;
		}
		if (service.isProductExist(name)) {
			JOptionPane.showMessageDialog(view, "Tên pet này đã tồn tại!");
			return;
		}

		try {
			int age = Integer.parseInt(ageStr);
			double price = Double.parseDouble(priceStr);

			if (containsNumber(name) || containsNumber(breed) || containsNumber(species)) {
				JOptionPane.showMessageDialog(view, "Không được nhập số cho Tên, Giống và Loài!");
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
		String name = view.getNameTextField();
		String species = view.getSpeciesTextField();
		String breed = view.getBreed_textField();
		String ageStr = view.getAgeTextField();
		String priceStr = view.getPriceTextField();
		String gender = view.getGenderComboBox().getSelectedItem().toString();
		int petID = Integer.parseInt(view.getPetTable().getValueAt(selectedRow, 0).toString());
		if (name.isEmpty() ||name.equals("Enter Name") || species.isEmpty() ||species.equals("Enter species") || breed.isEmpty() || breed.equals("Enter breed") || ageStr.isEmpty()|| ageStr.equals("Enter Age") || priceStr.isEmpty() ||priceStr.equals("Enter Price")) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
			return;
		}
		try{
			int age = Integer.parseInt(view.getAgeTextField());
			double price = Double.parseDouble(view.getPriceTextField());
			if (containsNumber(name) || containsNumber(breed) || containsNumber(species)) {
				JOptionPane.showMessageDialog(view, "Không được nhập số cho Tên, Giống và Loài!");
				return;
			}
			Pet pet = new Pet(petID, name, breed, species, age, price, gender);
			service.update(pet);
			loadTableData();
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
				// Thay vì xóa, cập nhật trạng thái
				service.updateTrangThai(id, "ngừng kinh doanh");
				loadTableData();
				view.clearFields();
			}
		} else {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn pet để ngừng kinh doanh.");
		}
	}

	private void filterPets() {
		String species = view.getSpeciesComboBox().getSelectedItem().toString();
		String selectedArrange = view.getArrangeComboBox().getSelectedItem().toString();
		String sqlPriceOrder = null;

		if (selectedArrange.equals("Tăng dần")) {
			sqlPriceOrder = "ASC";
		} else if (selectedArrange.equals("Giảm dần")) {
			sqlPriceOrder = "DESC";
		}

		if (species.equals("Tất cả")) {
			currentPetList = service.filterALL(null, sqlPriceOrder);
		} else {
			currentPetList = service.filterALL(species, sqlPriceOrder);
		}
		currentPage = 1;
		updateTablePage();
	}

	private void updateTablePage() {
		if (currentPetList == null) currentPetList = service.getAll();
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
					pet.getPetID(),    // ID
					pet.getName(),     // Name
					pet.getBreed(),    // Breed
					pet.getSpecies(),  // Species
					pet.getGender(),   // Gender
					pet.getAge(),      // Age
					pet.getPrice()     // Price
			});
		}
		view.getPageInfoLabel().setText("Page " + currentPage + "/" + (totalPage == 0 ? 1 : totalPage));
		view.getPrevPageButton().setEnabled(currentPage > 1);
		view.getNextPageButton().setEnabled(currentPage < totalPage);
	}
}