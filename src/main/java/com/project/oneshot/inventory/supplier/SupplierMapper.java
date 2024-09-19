package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SupplierMapper {
    public void registerSupplier(SupplierVO supplier); // 업체등록
    public List<SupplierVO> getAllSuppliers(); // 목록
    public List<SupplierVO> searchSuppliers(Map<String, Object> parameters); // 검색
    SupplierVO getSupplierByNo(Long supplierNo); // 수정 모달 내용
    int modifySupplier(SupplierVO supplierVO); // 업체 수정
}


