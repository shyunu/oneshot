package com.project.oneshot.inventory.product;

import com.project.oneshot.command.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductVO> findBySupplierNo(@Param("supplierNo") Long supplierNo);

    Page<ProductVO> findProductVOs(Pageable pageable);
}
