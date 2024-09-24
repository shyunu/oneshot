package com.project.oneshot.inventory.supplier;

import com.project.oneshot.command.SupplierVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SupplierMapper {
    public List<SupplierVO> getAllSuppliers(SupplierCriteria cri); // 목록
    public void registerSupplier(SupplierVO supplier); // 업체등록
    int getTotalSupplier(SupplierCriteria cri); // 전체 게시글 수
    SupplierVO getSupplierByNo(Long supplierNo); // 수정 모달 내용
    int modifySupplier(SupplierVO supplierVO); // 업체 수정

    int checkSupplierName(String supplierName);
}


