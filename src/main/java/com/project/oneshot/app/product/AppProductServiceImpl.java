package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service("productServiceApp")
public class AppProductServiceImpl implements AppProductService {

    @Autowired
    AppProductMapper appProductMapper;

    @Override
    public List<ProductVO> getProductList(String searchKeyword) {
        return appProductMapper.getProductList(searchKeyword);
    }

    @Override
    public List<SupplierVO> getSupplierList() {
        return  appProductMapper.getSupplierList();
    }

    @Override
    public SupplierVO getSupplierContent(int supplierNo) {
        return appProductMapper.getSupplierContent(supplierNo);
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        return appProductMapper.getCategoryList();
    }

    @Override
    public void postProduct(ProductVO vo, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            // MultipartFile을 byte 배열로 변환하여 ProductVO에 설정
            byte[] imageBytes = file.getBytes();
            vo.setProductImgApp(imageBytes); // ProductVO의 이미지 필드에 바이트 배열 설정
        }
        // 데이터베이스에 ProductVO 저장
        appProductMapper.postProduct(vo);
    }

    @Override
    public ProductVO getProductContent(int productNo) {
        return appProductMapper.getProductContent(productNo);
    }

//    @Override
//    public int checkProductName(String productName) {
//        return appProductMapper.checkProductName(productName);
//    }

}
