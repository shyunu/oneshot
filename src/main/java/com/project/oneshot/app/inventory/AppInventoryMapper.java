package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.purchase.PurchaseCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppInventoryMapper {
    public List<PurchaseVO> getAllPurchase(@Param("searchKeyword") String searchKeyword); // 목록

    void registerPurchase(@Param("list") List<PurchaseVO> purchases); // 구매신청

    List<SupplierVO> getAllSuppliers(); // 공급업체 목록

    SupplierVO getSupplierInfo(int supplierNo); // 공급업체 정보

    List<CategoryVO> getAllCategories(); // 카테고리 목록

    List<CategoryVO> getCategories(int supplierNo); // 공급업체별 카테고리 목록

    List<ProductVO> getProducts(@Param("supplierNo") int supplierNo, @Param("categoryNo") int categoryNo);// 상품 목록

    List<ProductVO> getProductsByCategory(Long categoryNo); // 카테고리별 상품 목록

    List<EmployeeVO> getAllEmployees(); // 사원 목록

    ProductVO getQuantity(int productNo); // 수량

//    void changeProductStatus(); // 상태

}


