package com.project.oneshot.inventory.product;


import com.project.oneshot.entity.jpa.CategoryVO;
import com.project.oneshot.entity.jpa.ProductVO;
import com.project.oneshot.entity.jpa.SupplierVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public List<SupplierVO> getAllSuppliers();

    SupplierVO getSupplierDetails(Long supplierNo);

    void registerProduct(ProductVO vo);

    List<CategoryVO> getAllCategories();

    List<ProductVO> getProductDetails(Long supplierNo);

    SupplierVO registerSupplier(SupplierVO supplier);

    Page<ProductVO> getAllProducts(int page, int size);

    CategoryVO getCategoryById(Long categoryNo);
}
