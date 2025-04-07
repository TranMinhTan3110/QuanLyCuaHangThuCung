package controller;

import model.entity.Product;
import service.ProductService;
import view.ProductView;

import java.util.ArrayList;

public class ProductController {
    private final ProductService productService;
    private final ProductView productView;

    public ProductController(ProductService productService, ProductView productView) {
        this.productService = productService;
        this.productView = productView;
    }

    // Load danh sách sản phẩm lên view
    public void loadProducts() {
        ArrayList<Product> products = productService.getAll();
        productView.updateProductTable(products);
    }

    // Thêm sản phẩm
    public void addProduct(Product product) {
        if (productService.insert(product)) {
            productView.showMessage("✔️ Thêm sản phẩm thành công!");
            loadProducts();
        } else {
            productView.showMessage("❌ Thêm sản phẩm thất bại!");
        }
    }

    // Cập nhật sản phẩm
    public void updateProduct(Product product) {
        if (productService.update(product)) {
            productView.showMessage("✔️ Cập nhật sản phẩm thành công!");
            loadProducts();
        } else {
            productView.showMessage("❌ Cập nhật sản phẩm thất bại!");
        }
    }

    // Xóa sản phẩm
    public void deleteProduct(Product product) {
        if (productService.delete(product)) {
            productView.showMessage("✔️ Xóa sản phẩm thành công!");
            loadProducts();
        } else {
            productView.showMessage("❌ Xóa sản phẩm thất bại!");
        }
    }

    // Tìm sản phẩm gần đúng theo tên
    public void searchProductByNameLike(String name) {
        ArrayList<Product> results = productService.searchByName(name);
        if (results.isEmpty()) {
            productView.showMessage("🔍 Không tìm thấy sản phẩm phù hợp.");
        } else {
            productView.updateProductTable(results);
        }
    }

    // Tìm chính xác theo tên
//    public void searchProductByNameExact(String name) {
//        Product result = productService.searchExact(name);
//        if (result == null) {
//            productView.showMessage("🔍 Không tìm thấy sản phẩm có tên: " + name);
//        } else {
//            ArrayList<Product> oneResult = new ArrayList<>();
//            oneResult.add(result);
//            productView.updateProductTable(oneResult);
//        }
    }

