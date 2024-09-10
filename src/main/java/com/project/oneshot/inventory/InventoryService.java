package com.project.oneshot.inventory;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    public List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierDetails(Long supplierNo);

    void registerProduct(ProductVO vo);

    List<CategoryVO> getAllCategories();

    List<ProductVO> getProductDetails(Long supplierNo);

    List<ProductVO> getAllProducts();

    SupplierVO registerSupplier(SupplierVO supplier);

    Page<ProductVO> getAllProducts(int page, int size);

    CategoryVO getCategoryById(Long categoryNo);
}
