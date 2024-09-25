package com.project.oneshot.inventory.product;


import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<ProductVO> getProductList(ProductCriteria cri);

    int getTotalProductCount(ProductCriteria cri);

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

    void postProduct(ProductVO vo, MultipartFile file);

    ProductVO getProductContent(int productNo);

    void putProduct(ProductVO vo, MultipartFile file);
}
