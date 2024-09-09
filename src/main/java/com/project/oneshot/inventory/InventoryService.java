package com.project.oneshot.inventory;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;

import java.util.List;
import java.util.function.Supplier;

public interface InventoryService {

    public List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierDetails(Long supplierNo);

    void registerProduct(ProductVO vo);

    List<CategoryVO> getAllCategories();

    List<ProductVO> getProductDetails(Long supplierNo);

    List<ProductVO> getAllProducts();
}
