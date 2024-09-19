package com.project.oneshot.inventory.purchase;
import com.project.oneshot.command.PurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;


    @Override
    public List<PurchaseVO> getAllPurchase() {
        return purchaseMapper.getAllPurchase();
    }
}
