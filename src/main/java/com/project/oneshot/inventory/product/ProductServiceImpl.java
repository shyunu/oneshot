package com.project.oneshot.inventory.product;


import com.project.oneshot.command.CategoryVO;
import com.project.oneshot.command.ProductVO;
import com.project.oneshot.command.SupplierVO;
import com.project.oneshot.inventory.supplier.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductVO> getProductList() {
        List<ProductVO> list = productMapper.getProductList();
        return list;
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
    public void registerProduct(ProductVO vo, MultipartFile file) {
        /*
        String filename;
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
        */
    }
}
