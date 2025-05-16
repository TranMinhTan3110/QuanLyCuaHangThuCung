package service;

import respository.dao.DaoInterface;

import respository.dao.ProductDAO;
import model.entity.Product;
import model.entity.User;

import java.util.ArrayList;

public class ProductService {
    private DaoInterface daoProduct;
    private ProductDAO productDao;

    // Constructor nhận vào một repository để dễ dàng thay đổi hoặc kiểm thử
    public ProductService(DaoInterface productRepo) {
        this.daoProduct = productRepo;
        this.productDao = new ProductDAO();

    }

    // Lấy danh sách product
    public ArrayList<Product> getAll() {
        return daoProduct.getAll();
    }

    // Thêm user mới
    public boolean insert(Product product) {
        return daoProduct.insert(product);
    }

    // Cập nhật thông tin user
    public boolean update(Product product) {
        return daoProduct.update(product);
    }

    // Xóa user
    public boolean delete(Product product) {
        return daoProduct.delete(product);
    }

    public Product selectByID(int productID) {
        return (Product) daoProduct.selectByID(productID);
    }

    public ArrayList<Product> searchByName(String name) {
        if (daoProduct instanceof ProductDAO) {
            return ((ProductDAO) daoProduct).selectByNameLike(name);
        }
        return new ArrayList<>();
    }

    public boolean isProductExist(String name) {
        return productDao.isProductExists(name);
    }

    public String getProductName(int id) {
        if (daoProduct instanceof ProductDAO) {
            return ((ProductDAO) daoProduct).getProductNameById(id);
        }
        return null;
    }

    public boolean updateByQua(int id, int sl) {
        if (daoProduct instanceof ProductDAO) {
            return ((ProductDAO) daoProduct).updateQuantity(id, sl);
        }
        return false;
    }
}
