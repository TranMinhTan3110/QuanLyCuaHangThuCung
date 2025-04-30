package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.PetDAO;
import model.entity.Pet;
import model.entity.Product;
import service.PetService;
import view.PetView;

import static sun.net.www.MimeTable.loadTable;

public class PetController {
    private PetView view;
    private PetService service;

    public PetController(PetView view, PetService service) {
        this.view = view;
        this.service = service;

        addEventListeners();
        loadTableData();
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

    private void addPet() {
        try {
            String name = view.getNameTextField().getText().trim();
            System.out.println(name);
            String species = view.getSpeciesTextField().getText().trim();
            System.out.println(species);
            String breed = view.getBreed_textField().getText().trim();
            System.out.println(breed);
            String gender = view.getGenderComboBox().getSelectedItem().toString();
            System.out.println(gender);
            String ageStr = view.getAgeTextField().getText().trim();
            System.out.println(ageStr);
            String priceStr = view.getPriceTextField().getText().trim();
            System.out.println(priceStr);


            if (name.isEmpty() || species.isEmpty() || ageStr.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            float age = Float.parseFloat(ageStr);
            double price = Double.parseDouble(priceStr);
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid input. Please check the fields.");
        }
    }

    private void editPet() {
        int selectedRow = view.getPetTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a pet to edit.");
            return;
        }

        try {
            String name = view.getNameTextField().getText();
            String species = view.getSpeciesTextField().getText();
            String breed = view.getBreedComboBox().getSelectedItem().toString();
            float age = Float.parseFloat(view.getAgeTextField().getText());
            double price = Double.parseDouble(view.getPriceTextField().getText());
            String  gender = view.getGenderComboBox().getSelectedItem().toString();
            int petID = service.searchByNameLike(name).get(0).getPetID(); // hoặc lấy ID qua TableModel nếu có

            Pet pet = new Pet(petID, name, species, breed, age, price, gender);
            service.update(pet);

            loadTableData();
            view.clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid input or multiple pets with the same name.");
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
        String breed = String.valueOf(view.getBreedComboBox());
        String species = String.valueOf(view.getSpeciesComboBox());
        String priceOrder = String.valueOf(view.getArrangeComboBox());
        List<Pet> filtered = service.filterALL(breed, species, priceOrder);
        DefaultTableModel model = (DefaultTableModel) view.getPetTable().getModel();
        model.setRowCount(0);
        for (Pet pet : filtered) {
            model.addRow(new Object[]{
                    pet.getPetID(),
                    pet.getName(),
                    pet.getSpecies(),
                    pet.getPrice(),
                    pet.getBreed(),
                    pet.getAge(),
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
