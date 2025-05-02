package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.PetDAO;
import model.entity.Pet;
import model.entity.Product;
import service.PetService;
import view.PetView;


public class PetController {
    private PetView view;
    private PetService service;

    public PetController(PetView view, PetService service) {
        this.view = view;
        this.service = service;
        initController();
        addEventListeners();
        loadTableData();
    }

    private void initController() {
        // Khi chọn dòng trong bảng, load dữ liệu vào form
        view.getPetTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getPetTable().getSelectedRow() != -1) {
                loadSelectedPetIntoForm();
            }
        });
    }
    //load dữ liệu lên ô nhập liệu
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
    }

    class SearchListener implements KeyListener {
        @Override
        public void keyReleased(KeyEvent e) {
            String keyword = view.getSearchKeyword();
            List<Pet> list = service.searchByNameLike(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getPetTable().getModel();
            model.setRowCount(0);
            for (Pet pet : list) {
                model.addRow(new Object[]{
                        pet.getPetID(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getPrice(),
                        pet.getBreed(),
                        pet.getAge()
                });
            }
        }

        @Override public void keyTyped(KeyEvent e) {}
        @Override public void keyPressed(KeyEvent e) {}
    }
    private void loadTableData() {
        ArrayList<Pet> pets = service.getAll();
        DefaultTableModel model = (DefaultTableModel) view.getPetTable().getModel();
        model.setRowCount(0);

        for (Pet pet : pets) {
            model.addRow(new Object[]{
                    pet.getPetID(),         // Cột "ID"
                    pet.getName(),          // Cột "Name"
                    pet.getSpecies(),       // Cột "Species"
                    pet.getPrice(),         // Cột "Price"
                    pet.getBreed(),         // Cột "Breed"
                    pet.getGender(),        // Cột "Gender"
                    pet.getAge()            // Cột "Age"
            });
        }
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

            // Kiểm tra xem name, breed, species có chứa số hay không
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

    // Hàm tiện ích để kiểm tra xem một chuỗi có chứa số hay không
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
//            String breed = view.getBreedComboBox().getSelectedItem().toString();
            String breed = view.getBreed_textField();
//            view.getBreedComboBox().addItem(breed);
            String ageStr = view.getAgeTextField();
            String priceStr = view.getPriceTextField();
            String  gender = view.getGenderComboBox().getSelectedItem().toString();
        int petID = (int) view.getPetTable().getValueAt(selectedRow, 0);
        if (name.isEmpty() ||name.equals("Enter Name") || species.isEmpty() ||species.equals("Enter species") || breed.isEmpty() || breed.equals("Enter breed") || ageStr.isEmpty()|| ageStr.equals("Enter Age") || priceStr.isEmpty() ||priceStr.equals("Enter Price")) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
            try{
            int age = Integer.parseInt(view.getAgeTextField());
            double price = Double.parseDouble(view.getPriceTextField());
            Pet pet = new Pet(petID, name, breed,species, age, price, gender);
                // Kiểm tra xem name, breed, species có chứa số hay không
                if (containsNumber(name) || containsNumber(breed) || containsNumber(species)) {
                    JOptionPane.showMessageDialog(view, "Không được nhập số cho Tên, Giống và Loài!");
                    return;
                }
            service.update(pet);
            loadTableData();
            view.clearFields();
        } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho Tuổi và Giá!");
            }
    }

    private void deletePet() {
        int row =  view.getPetTable().getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa pet này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) view.getPetTable().getValueAt(row, 0);
                Pet pet = service.selectByID(id);
                service.delete(pet);
                loadTableData();
                view.clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn pet để xóa.");
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

        List<Pet> filtered;
        if (species.equals("Tất cả")) {
            filtered = service.filterALL(null, sqlPriceOrder); // null = không lọc theo loài
        } else {
            filtered = service.filterALL(species, sqlPriceOrder); // lọc theo species
        }

        DefaultTableModel model = (DefaultTableModel) view.getPetTable().getModel();
        model.setRowCount(0);
        for (Pet pet : filtered) {
            model.addRow(new Object[]{
                    pet.getPetID(),
                    pet.getName(),
                    pet.getSpecies(),
                    pet.getPrice(),
                    pet.getBreed(),
                    pet.getGender(),
                    pet.getAge()
            });
        }
    }



//    private void fillFormFromSelectedRow(int selectedRow) {
//        String petName = view.getValueAt(selectedRow, 1);
//        String species = view.getValueAt(selectedRow, 2);
//        String price = view.getValueAt(selectedRow, 3);
//        String breed = view.getValueAt(selectedRow, 4);
//        String gender = view.getValueAt(selectedRow, 5);
//        String age = view.getValueAt(selectedRow, 6);
//
//        view.setName_textField(petName);
//        view.setSpecies_textField(species);
//        view.setBreed_textField(breed);
//        view.setPrice_textField(price);
//        view.setAge_textField(age);
//        view.setGender_comboBox();
//    }

}
