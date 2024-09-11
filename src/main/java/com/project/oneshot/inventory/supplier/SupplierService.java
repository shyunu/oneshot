package com.project.oneshot.inventory.supplier;

import com.project.oneshot.entity.jpa.SupplierVO;

import java.util.List;

public interface SupplierService {
    public void registerSupplier(SupplierVO supplier);
    public List<SupplierVO> getAllSuppliers();
}
