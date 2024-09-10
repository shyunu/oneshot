package com.project.oneshot.inventory.product;

import com.project.oneshot.vo.jpa.CategoryVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryVO, Long> {
}
