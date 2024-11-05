package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.purchase.PurchaseCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInventoryService {

    void registerPurchase(@Param("list") List<PurchaseVO> purchases); // 구매

    List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierInfo(int supplierNo);

    List<CategoryVO> getCategories(int supplierNo);

    List<ProductVO> getProductsByCategory(Long categoryNo);

    List<ProductVO> getProducts(int supplierNo, int categoryNo);

    ProductVO getQuantity(int productNo);

    List<CategoryVO> getAllCategories();

    List<EmployeeVO> getAllEmployees();

    public List<PurchaseVO> getAllPurchase(@Param("searchKeyword") String searchKeyword); // 목록

}
