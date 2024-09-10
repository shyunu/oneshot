package com.project.oneshot.inventory;

import com.project.oneshot.entity.SupplierVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {
    void registSupplier(SupplierVO supplier);
    List<SupplierVO> getAllSupplier();
}
