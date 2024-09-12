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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<SupplierVO> getAllSuppliers() {
        return null;
    }

    @Override
    public SupplierVO getSupplierDetails(Long supplierNo) {
        return null;
    }

    @Override
    public void registerProduct(ProductVO vo) {
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        return null;
    }

    @Override
    public List<ProductVO> getProductDetails(Long supplierNo) {
        return null;
    }

    @Override
    public Page<ProductVO> getAllProducts(int page, int size) {
        return null;
    }

    @Override
    public CategoryVO getCategoryById(Long categoryNo) {
        return null;
    }

    @Override
    public SupplierVO registerSupplier(SupplierVO supplier) {
        return null;
    }
}
