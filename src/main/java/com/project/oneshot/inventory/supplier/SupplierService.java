package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    public List<SupplierVO> getAllSuppliers(SupplierCriteria cri);
    int getTotalSupplier(SupplierCriteria cri);
    public void registerSupplier(SupplierVO supplier);
    SupplierVO getSupplierByNo(Long supplierNo);
    public boolean modifySupplier(SupplierVO supplierVO);
}