package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Base64;

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

//    @Override
//    public void postProduct(ProductVO vo) {
//        try {
//            // 예: 데이터베이스에 제품 정보 저장
//            appProductMapper.postProduct(vo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Product save failed: " + e.getMessage());
//        }
//    }

    @Override
    public void postProduct(ProductVO vo) {
        try {
            // 이미지가 null이 아니고 비어 있지 않으면
            if (vo.getProductImgApp() != null && vo.getProductImgApp().length > 0) {
                // 이미지 데이터가 Base64로 인코딩된 문자열이라면 byte[]로 변환
                byte[] imageBytes = Base64.getDecoder().decode(vo.getProductImgApp());
                vo.setProductImgApp(imageBytes);  // byte[]로 저장
                System.out.println("이미지 데이터 byte[]: " + Arrays.toString(imageBytes)); // 디버깅 용도
            }

            // DB에 저장
            appProductMapper.postProduct(vo);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Product save failed: " + e.getMessage());
        }
    }


//    @Override
//    public void postProduct(ProductVO vo) {
//        try {
//            if (file != null && !file.isEmpty()) {
//                // 이미지 데이터를 바로 Base64로 인코딩하여 저장
//                byte[] imageBytes = file.getBytes();
//                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//                vo.setProductImg(base64Image);  // 파일 경로가 아닌 Base64 데이터를 직접 저장
//            }
//
//            appProductMapper.postProduct(vo);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save product image", e);
//        }
//    }


    @Override
    public ProductVO getProductContent(int productNo) {
        return appProductMapper.getProductContent(productNo);
    }

    @Override
    public int checkProductName(String productName) {
        return appProductMapper.checkProductName(productName);
    }

}
