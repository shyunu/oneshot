package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppProductMapper {

    List<ProductVO> getProductList();

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

    void postProduct(ProductVO vo);

    ProductVO getProductContent(int productNo);

    void putProduct(ProductVO vo);

    int checkProductName(String productName);
}
