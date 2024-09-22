package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.*;

import java.util.List;

public interface PurchaseService {
    public List<PurchaseVO> getAllPurchase(PurchaseCriteria cri);

    int getTotalPurchase(PurchaseCriteria cri);

    public void registerPurchase(PurchaseVO purchase);

    List<SupplierVO> getAllSuppliers();

    List<CategoryVO> getAllCategories();

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<EmployeeVO> getAllEmployees();
}
