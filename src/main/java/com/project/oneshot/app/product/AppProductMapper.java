package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppProductMapper {

    List<ProductVO> getProductList(@Param("searchKeyword") String searchKeyword);

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

    int checkProductName(String productName);

    void postProduct(ProductVO vo);

    ProductVO getProductContent(int productNo);
}
