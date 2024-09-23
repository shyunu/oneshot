package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    public List<PurchaseVO> getAllPurchase(PurchaseCriteria cri); // 목록

    int getTotalPurchase(PurchaseCriteria cri); // 전체 게시글 수

    public void registerPurchase(List<PurchaseVO> list); // 구매신청

    List<SupplierVO> getAllSuppliers(); // 공급업체 목록

    List<CategoryVO> getAllCategories(); // 카테고리 목록

    List<ProductVO> getProductsByCategory(Long categoryNo); // 카테고리별 상품 목록

    List<EmployeeVO> getAllEmployees(); // 사원 목록

    Integer getPurchaseNo();
}
