package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;

import java.util.List;

public interface AppInventoryService {

    public void registerPurchase(List<PurchaseVO> list);

    List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierInfo(int supplierNo);

    List<CategoryVO> getCategories(int supplierNo);

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<ProductVO> getProducts(int supplierNo, int categoryNo);

    ProductVO getQuantity(int productNo);

    List<CategoryVO> getAllCategories();

    List<EmployeeVO> getAllEmployees();
}
