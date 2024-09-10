package com.project.oneshot.inventory.supplier;

import com.project.oneshot.vo.jpa.SupplierVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupplierMapper {
    void registSupplier(SupplierVO supplier);
    List<SupplierVO> getAllSupplier();
}
