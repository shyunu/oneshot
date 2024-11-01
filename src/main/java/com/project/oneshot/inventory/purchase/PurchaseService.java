package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.*;

import java.util.List;

public interface PurchaseService {
    public List<PurchaseVO> getAllPurchase(PurchaseCriteria cri);

    int getTotalPurchase(PurchaseCriteria cri);

    public void registerPurchase(List<PurchaseVO> list);

    List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierInfo(int supplierNo);

    List<CategoryVO> getAllCategories();

    List<CategoryVO> getCategories(int supplierNo);

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<ProductVO> getProducts(int supplierNo, int categoryNo);

    List<EmployeeVO> getAllEmployees();

    ProductVO getQuantity(int productNo);
}
