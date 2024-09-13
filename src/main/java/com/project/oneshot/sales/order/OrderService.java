package com.project.oneshot.sales.order;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.OrderVO;

import java.util.List;

public interface OrderService {

    // ----- 판매내역 ----- //
    public int orderRegist(OrderVO vo);
    public List<ClientVO> getClientList();
}
