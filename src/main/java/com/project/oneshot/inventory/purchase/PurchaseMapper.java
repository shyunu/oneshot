package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.PurchaseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {
    public List<PurchaseVO> getAllPurchase();
}
