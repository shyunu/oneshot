package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppProductService {

    List<ProductVO> getProductList(@Param("searchKeyword") String searchKeyword);

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

    void postProduct(ProductVO vo, MultipartFile file) throws Exception;

    ProductVO getProductContent(int productNo);

//    int checkProductName(String productName);

//    void postProduct(ProductVO vo);

}

