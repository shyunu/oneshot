package com.project.oneshot.inventory.supplier;

import com.project.oneshot.vo.jpa.SupplierVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierVO, Long> {
}
