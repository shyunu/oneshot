package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;

import java.util.List;

public interface AppInventoryService {

    SupplierVO getSupplier(int supplierNo);

//    public void registerPurchase(List<PurchaseVO> list);

    List<SupplierVO> getAllSuppliers();

    List<CategoryVO> getCategories(int supplierNo);

    List<ProductVO> getProducts(int supplierNo, int categoryNo);

    ProductVO getQuantity(int productNo);

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<EmployeeVO> getAllEmployees();

    List<CategoryVO> getAllCategories();
}
