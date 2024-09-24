package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.*;

import java.util.List;

public interface PurchaseService {
    public List<PurchaseVO> getAllPurchase(PurchaseCriteria cri);

    int getTotalPurchase(PurchaseCriteria cri);

    public void registerPurchase(List<PurchaseVO> list);

    List<SupplierVO> getAllSuppliers();

    List<CategoryVO> getCategories(int supplierNo);

    List<ProductVO> getProducts(int supplierNo, int categoryNo);

    ProductVO getQuantity(int productNo);

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<EmployeeVO> getAllEmployees();

    List<CategoryVO> getAllCategories();
}
