package com.project.oneshot.inventory.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductVO> getProductList(ProductCriteria cri);

    int getTotalProductCount(ProductCriteria cri);

    List<SupplierVO> getSupplierList();

    SupplierVO getSupplierContent(int supplierNo);

    List<CategoryVO> getCategoryList();

    void postProduct(ProductVO vo);

    ProductVO getProductContent(int productNo);

    void putProduct(ProductVO vo);

    int checkProductName(String productName);
}
