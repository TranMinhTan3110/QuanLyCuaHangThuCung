package service;

import model.entity.Customer;
import model.entity.User;
import respository.dao.*;
import model.entity.Appointment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private DaoInterface<Appointment> daoAppointment;
    private AppointmentDAO appointmentDao;
    private AppointmentServiceDAO appointmentServiceDao;
    private CustomerService customerService;
    private UserService userService;
    public AppointmentService(DaoInterface<Appointment> appointmentRepo) {
        this.daoAppointment = appointmentRepo;
        this.appointmentDao = new AppointmentDAO();
        this.appointmentServiceDao = new AppointmentServiceDAO();
        this.customerService = new CustomerService(new CustomerDao());
        this.userService = new UserService(new UserDAO());

    }

    public List<Appointment> getAllAppointments() throws SQLException {
        return appointmentDao.getAll();
    }

    public List<String> getServicesForAppointment(int appointmentId) throws SQLException {
        return appointmentDao.getServiceNames(appointmentId);
    }

    public boolean updateStatus(int appointmentId, String newStatus) throws SQLException {
        return  appointmentDao.updateStatus(appointmentId, newStatus);
    }

    public int getAppointmentIdByInfo(String petName, String customerName, String appointmentDate) throws SQLException {
        return appointmentDao.getAppointmentIdByInfo(petName, customerName, appointmentDate);
    }

    public Customer getCustomerNameById(int customerId) throws SQLException {
        return customerService.selectedByID(customerId);
    }
    public boolean updateAppointmentStatus(int appointmentId, int staffId, String status, List<String> services) {
        // Gọi phương thức có xử lý completionDate
        return appointmentDao.updateAppointmentWithServices(appointmentId, staffId, status, services);
    }

    public boolean updateAppointmentCustomer(int appointmentId, int newCustomerId, String petName) {
        return appointmentDao.updateAppointmentWithCustomer(appointmentId, newCustomerId, petName);
    }
    /**
     * Lưu cuộc hẹn và các dịch vụ đi kèm
     */
    public boolean insertWithServices(Appointment appointment, List<String> services) {
        try {
            // Sử dụng PreparedStatement.RETURN_GENERATED_KEYS để lấy ID được sinh ra
            int generatedId = appointmentDao.insertAndGetId(appointment);
            if (generatedId > 0) {
                appointment.setAppointmentID(generatedId);
                return appointmentServiceDao.insertServices(generatedId, services);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Appointment> getAll() {
        return daoAppointment.getAll();
    }

    public boolean insert(Appointment appointment) {
        return daoAppointment.insert(appointment);
    }

    public boolean update(Appointment appointment) {
        return daoAppointment.update(appointment);
    }

    public boolean delete(Appointment appointment) {
        return daoAppointment.delete(appointment);
    }

    public Appointment selectByID(int id) {
        return daoAppointment.selectByID(id);
    }

    // Add custom methods here if needed


    // Lấy danh sách tất cả khách hàng
    public ArrayList<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    // Lấy danh sách tất cả nhân viên
    public ArrayList<User> getAllStaff() {
        return userService.getAll();
    }

    // Tìm khách hàng theo tên
    public List<Customer> searchCustomersByName(String name) {
        return customerService.searchByCustomerName(name);
    }

    // Tìm khách hàng theo số điện thoại
    public List<Customer> searchCustomersByPhone(String phone) {
        return customerService.searchByCustomerPhone(phone);
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    public boolean isPhoneExist(String phone) {
        return customerService.checkPhone(phone);
    }

    // Lấy thông tin khách hàng từ cuộc hẹn
    public Customer getCustomerFromAppointment(Appointment appointment) {
        if (appointment != null) {
            return customerService.selectedByID(appointment.getCustomerID());
        }
        return null;
    }



}