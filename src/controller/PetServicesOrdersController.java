//package controller;
//
//import service.AppointmentService;
//import view.PetServicesOrdersPanel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PetServicesOrdersController {
//    private final PetServicesOrdersPanel panel;
//    private final AppointmentService appointmentService;
//    private List<Object[]> allData; // Stores all data retrieved from the service
//    private int rowsPerPage;
//
//    public PetServicesOrdersController(PetServicesOrdersPanel panel, AppointmentService appointmentService) {
//        this.panel = panel;
//        this.appointmentService = appointmentService;
//        this.rowsPerPage = 4; // Default rows per page
//
//        // Initialize data and set up event listeners
//        loadData();
//        setupPaginationListeners();
//    }
//
//    private void loadData() {
//        // Fetch data from the service
//        ArrayList<Object[]> data = new ArrayList<>();
//        appointmentService.getAll().forEach(appointment -> {
//            data.add(new Object[]{
//                    appointment.get(),
//                    appointment.getOwnerName(),
//                    appointment.getServiceName(),
//                    appointment.getAppointmentDate(),
//                    appointment.getStatus()
//            });
//        });
//        this.allData = data;
//
//        // Update the panel with the first page of data
//        updatePanelData(1);
//    }
//
//    private void updatePanelData(int page) {
//        int start = (page - 1) * rowsPerPage;
//        int end = Math.min(start + rowsPerPage, allData.size());
//        Object[][] pageData = allData.subList(start, end).toArray(new Object[0][]);
//
//        panel.setData(pageData);
//        panel.setCurrentPage(page);
//    }
//
//    private void setupPaginationListeners() {
//        panel.getBtnPrev().addActionListener(e -> {
//            int currentPage = panel.getCurrentPage();
//            if (currentPage > 1) {
//                updatePanelData(currentPage - 1);
//            }
//        });
//
//        panel.getBtnNext().addActionListener(e -> {
//            int currentPage = panel.getCurrentPage();
//            int totalPages = (int) Math.ceil((double) allData.size() / rowsPerPage);
//            if (currentPage < totalPages) {
//                updatePanelData(currentPage + 1);
//            }
//        });
//    }
//}