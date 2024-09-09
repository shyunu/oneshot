package com.project.oneshot.inventory;

import com.project.oneshot.entity.SupplierVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Supplier;

public interface SupplierRepository extends JpaRepository<SupplierVO, Long> {
}
