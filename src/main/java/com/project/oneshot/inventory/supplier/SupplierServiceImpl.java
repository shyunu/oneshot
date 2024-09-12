package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}