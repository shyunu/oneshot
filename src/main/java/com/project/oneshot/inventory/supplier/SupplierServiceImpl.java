package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public void registerSupplier(SupplierVO supplier) {
        supplierMapper.registerSupplier(supplier);
    }

    @Override
    public List<SupplierVO> getAllSuppliers() {
        return supplierMapper.getAllSuppliers();
    }

    public List<SupplierVO> searchSuppliers(Map<String, Object> parameters) {
        return supplierMapper.searchSuppliers((Map<String, Object>) parameters);
    }

    @Override
    public SupplierVO getSupplierByNo(Long supplierNo) {
        return supplierMapper.getSupplierByNo(supplierNo);
    }

    @Override
    public boolean modifySupplier(SupplierVO supplierVO) {
         return supplierMapper.modifySupplier(supplierVO) > 0;
    }
}