package com.project.oneshot.inventory.product;


import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
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
