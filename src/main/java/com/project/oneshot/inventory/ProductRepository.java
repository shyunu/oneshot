package com.project.oneshot.inventory;

import com.project.oneshot.entity.ProductVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductVO, Long> {

    List<ProductVO> findBySupplierNo(Long supplierNo);

    @Query("SELECT p FROM ProductVO p JOIN p.categoryVO c")
    List<ProductVO> findProductVOs();
}
