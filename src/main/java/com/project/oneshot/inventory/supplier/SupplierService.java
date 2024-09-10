package com.project.oneshot.inventory.supplier;

import com.project.oneshot.vo.jpa.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    public void registerSupplier(SupplierVO supplier) {
        supplierMapper.registSupplier(supplier);
    }

    public List<SupplierVO> getAllSuppliers() {
        return supplierMapper.getAllSupplier();
    }
}
