package com.project.oneshot.inventory.purchase;

import com.project.oneshot.command.PurchaseVO;

import java.util.List;

public interface PurchaseService {
    public List<PurchaseVO> getAllPurchase();
}
