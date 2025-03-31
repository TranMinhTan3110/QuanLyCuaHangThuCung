//package controller;
//
//import model.entity.Product;
//import service.ProductService;
//import view.ProductView;
//
//import java.util.ArrayList;
//
//public class ProductController {
//    private ProductService productService;
//    private ProductView productView;
//
//    public ProductController(ProductService productService, ProductView productView) {
//        this.productService = productService;
//        this.productView = productView;
//    }
//
//    // Lấy danh sách sản phẩm từ database
//    public void loadProducts() {
//        ArrayList<Product> products = productService.getAll();
//
//    }
//
//    // Thêm sản phẩm mới
//    public void addProduct(Product product) {
//        if (productService.insert(product)) {
//            productView.showMessage("Thêm sản phẩm thành công!");
//        } else {
//            productView.showMessage("Thêm sản phẩm thất bại!");
//        }
//    }
//
//    // Cập nhật sản phẩm
//    public void updateProduct(Product product) {
//        if (productService.update(product)) {
//            productView.showMessage("Cập nhật sản phẩm thành công!");
//        } else {
//            productView.showMessage("Cập nhật sản phẩm thất bại!");
//        }
//    }
//
//    // Xóa sản phẩm
//    public void deleteProduct(Product product) {
//        if (productService.delete(product)) {
//            productView.showMessage("Xóa sản phẩm thành công!");
//        } else {
//            productView.showMessage("Xóa sản phẩm thất bại!");
//        }
//    }
//}
