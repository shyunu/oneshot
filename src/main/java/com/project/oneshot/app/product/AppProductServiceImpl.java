package com.project.oneshot.app.product;

import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.product.ProductCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service("productServiceApp")
public class AppProductServiceImpl implements AppProductService {

    @Autowired
    AppProductMapper appProductMapper;

    @Override
    public List<ProductVO> getProductList() {
        return appProductMapper.getProductList();
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
    public void postProduct(ProductVO vo, MultipartFile file) {
        String filename = null;
        try {
            filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String directoryPath = "D:/file_repo/";
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String filePath = directoryPath + filename;
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        vo.setProductImg(filename);
        appProductMapper.postProduct(vo);
    }

    @Override
    public ProductVO getProductContent(int productNo) {
        return appProductMapper.getProductContent(productNo);
    }

    @Override
    public void putProduct(ProductVO vo, MultipartFile file) {
        if(vo.getProductImg() == null || vo.getProductImg() == "") {
            ProductVO productVO = appProductMapper.getProductContent(vo.getProductNo());
            vo.setProductImg(productVO.getProductImg());
        } else {
            String filename = null;
            try {
                filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String directoryPath = "D:/file_repo/";
                File dir = new File(directoryPath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String filePath = directoryPath + filename;
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            vo.setProductImg(filename);
        }
        appProductMapper.putProduct(vo);
    }

    @Override
    public int checkProductName(String productName) {
        return appProductMapper.checkProductName(productName);
    }
}
