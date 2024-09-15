package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    // ----- 판매내역 ----- //
    @Override
    public int orderRegist(OrderVO vo) {
        int result = orderMapper.orderRegist(vo);
        return result;
    }

    @Override
    public List<ClientVO> getClientList() {
        List<ClientVO> list = orderMapper.getClientList();
        return list;
    }

    @Override
    public ClientVO getClientContent(int clientNo) {
        ClientVO vo = orderMapper.getClientContent(clientNo);
        return vo;
    }

    @Override
    public List<ContractVO> getProductList(int clientNo) {
        List<ContractVO> list = orderMapper.getProductList(clientNo);
        return list;
    }

}
