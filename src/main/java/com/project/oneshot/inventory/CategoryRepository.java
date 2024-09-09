package com.project.oneshot.inventory;

import com.project.oneshot.entity.CategoryVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryVO, Long> {
}
