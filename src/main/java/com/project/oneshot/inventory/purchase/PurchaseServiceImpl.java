package com.project.oneshot.inventory.purchase;
import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<PurchaseVO> getAllPurchase(PurchaseCriteria cri) {
        return purchaseMapper.getAllPurchase(cri);
    }

    @Override
    public int getTotalPurchase(PurchaseCriteria cri) {
        int TotalPosts = purchaseMapper.getTotalPurchase(cri);
        return TotalPosts;
    }

    @Override
    public void registerPurchase(PurchaseVO purchase) {
        purchaseMapper.registerPurchase(purchase);
    }

    @Override
    public List<SupplierVO> getAllSuppliers() {
        return purchaseMapper.getAllSuppliers();
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        return purchaseMapper.getAllCategories();
    }

    @Override
    public List<ProductVO> getProductsByCategory(Long categoryNo) {
        return purchaseMapper.getProductsByCategory(categoryNo);
    }

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return purchaseMapper.getAllEmployees();
    }
}
