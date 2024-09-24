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
    public void registerPurchase(List<PurchaseVO> list) {
        purchaseMapper.registerPurchase(list);
        purchaseMapper.changeProductStatus();
    }

    @Override
    public List<SupplierVO> getAllSuppliers() {
        return purchaseMapper.getAllSuppliers();
    }

    @Override
    public SupplierVO getSupplier(int supplierNo) {
        return purchaseMapper.getSupplier(supplierNo);
    }

    @Override
    public List<CategoryVO> getCategories(int supplierNo) {
        return purchaseMapper.getCategories(supplierNo);
    }

    @Override
    public List<ProductVO> getProducts(int supplierNo, int categoryNo) {
        return purchaseMapper.getProducts(supplierNo, categoryNo);
    }

    @Override
    public ProductVO getQuantity(int productNo) {
        return purchaseMapper.getQuantity(productNo);
    }

    @Override
    public List<ProductVO> getProductsByCategory(Long categoryNo) {
        return purchaseMapper.getProductsByCategory(categoryNo);
    }

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return purchaseMapper.getAllEmployees();
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        return purchaseMapper.getAllCategories();
    }
}
