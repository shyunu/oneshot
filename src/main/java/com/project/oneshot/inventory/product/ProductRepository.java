package com.project.oneshot.inventory.product;


import com.project.oneshot.entity.jpa.ProductVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductVO, Long> {

    @Query("SELECT p FROM ProductVO p WHERE p.supplierVO.supplierNo = :supplierNo")
    List<ProductVO> findBySupplierNo(@Param("supplierNo") Long supplierNo);

    @Query("SELECT p FROM ProductVO p JOIN p.categoryVO c JOIN p.supplierVO s")
    Page<ProductVO> findProductVOs(Pageable pageable);
}
