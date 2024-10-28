package com.project.oneshot.app.inventory;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.purchase.PurchaseCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("purchaseServiceApp")
public class AppInventoryServiceImpl implements AppInventoryService {

    @Autowired
    private AppInventoryMapper appInventoryMapper;

//    @Override
//    public void registerPurchase(List<PurchaseVO> list) {
//        appInventoryMapper.registerPurchase(list);
//        appInventoryMapper.changeProductStatus();
//    }

    @Override
    public List<SupplierVO> getAllSuppliers() {
        return appInventoryMapper.getAllSuppliers();
    }

    @Override
    public SupplierVO getSupplier(int supplierNo) {
        return appInventoryMapper.getSupplier(supplierNo);
    }

    @Override
    public List<CategoryVO> getCategories(int supplierNo) {
        return appInventoryMapper.getCategories(supplierNo);
    }

    @Override
    public List<ProductVO> getProducts(int supplierNo, int categoryNo) {
        return appInventoryMapper.getProducts(supplierNo, categoryNo);
    }

    @Override
    public ProductVO getQuantity(int productNo) {
        return appInventoryMapper.getQuantity(productNo);
    }

    @Override
    public List<ProductVO> getProductsByCategory(Long categoryNo) {
        return appInventoryMapper.getProductsByCategory(categoryNo);
    }

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return appInventoryMapper.getAllEmployees();
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        return appInventoryMapper.getAllCategories();
    }
}
