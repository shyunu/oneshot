package com.project.oneshot.inventory.product;


import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductVO> getProductList(ProductCriteria cri) {
        List<ProductVO> list = productMapper.getProductList(cri);
        return list;
    }

    @Override
    public int getTotalProductCount(ProductCriteria cri) {
        int TotalPosts = productMapper.getTotalProductCount(cri);
        return TotalPosts;
    }

    @Override
    public List<SupplierVO> getSupplierList() {
        List<SupplierVO> list = productMapper.getSupplierList();
        return list;
    }

    @Override
    public SupplierVO getSupplierContent(int supplierNo) {
        SupplierVO vo = productMapper.getSupplierContent(supplierNo);
        return vo;
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        List<CategoryVO> list = productMapper.getCategoryList();
        return list;
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
        productMapper.postProduct(vo);
    }

    @Override
    public ProductVO getProductContent(int productNo) {
        ProductVO vo = productMapper.getProductContent(productNo);
        return vo;
    }

    @Override
    public void putProduct(ProductVO vo, MultipartFile file) {
        if(vo.getProductImg() == null || vo.getProductImg() == "") {
            ProductVO productVO = productMapper.getProductContent(vo.getProductNo());
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
        productMapper.putProduct(vo);
    }

    @Override
    public int checkProductName(String productName) {
        return productMapper.checkProductName(productName);
    }
}
