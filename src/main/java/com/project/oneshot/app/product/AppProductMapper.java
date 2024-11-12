package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface AppProductMapper {

    List<ProductVO> getProductList(@Param("searchKeyword") String searchKeyword);

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

//    int checkProductName(String productName);

    void postProduct(@Param("vo") ProductVO vo) throws Exception;

    ProductVO getProductContent(int productNo);
}
