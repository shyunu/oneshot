package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    public void registerSupplier(SupplierVO supplier);
    public List<SupplierVO> getAllSuppliers();
    public List<SupplierVO> searchSuppliers(Map<String, Object> parameters);
    SupplierVO getSupplierByNo(Long supplierNo);
    public boolean modifySupplier(SupplierVO supplierVO);
}