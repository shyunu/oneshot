package com.project.oneshot.inventory;

import com.project.oneshot.entity.CategoryVO;
import com.project.oneshot.entity.ProductVO;
import com.project.oneshot.entity.SupplierVO;
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
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private SupplierMapper supplierMapper;

    public List<SupplierVO> getAllSuppliers() {
        List<SupplierVO> list = supplierRepository.findAll();
        list = list.stream()
                   .sorted(Comparator.comparing(SupplierVO::getSupplierName))
                   .collect(Collectors.toList());
        return list;
    }

    @Override
    public SupplierVO getSupplierDetails(Long supplierNo) {
        Optional<SupplierVO> vo = supplierRepository.findById(supplierNo);
        return vo.get();
    }

    @Override
    public void registerProduct(ProductVO vo) {
        productRepository.save(vo);
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        List<CategoryVO> list = categoryRepository.findAll();
        list = list.stream()
                   .sorted(Comparator.comparing(CategoryVO::getCategoryNo))
                   .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<ProductVO> getProductDetails(Long supplierNo) {
        List<ProductVO> list = productRepository.findBySupplierNo(supplierNo);
        return list;
    }

    @Override
    public Page<ProductVO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productNo").descending());
        return productRepository.findProductVOs(pageable);
    }

    @Override
    public CategoryVO getCategoryById(Long categoryNo) {
        return categoryRepository.findById(categoryNo).orElse(null);
    }

    @Override
    public SupplierVO registerSupplier(SupplierVO supplier) {
        return supplierRepository.save(supplier);
    }

}
