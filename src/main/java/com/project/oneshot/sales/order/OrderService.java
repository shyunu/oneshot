package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;

import java.util.List;

public interface OrderService {

    // ----- 판매내역 ----- //
    public int orderRegist(OrderVO vo);
    public List<ClientVO> getClientList();
    public ClientVO getClientContent(int clientNo);

    public List<ContractVO> getProductList(int clientNo);

}
