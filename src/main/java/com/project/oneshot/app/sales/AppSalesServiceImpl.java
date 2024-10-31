package com.project.oneshot.app.sales;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSalesServiceImpl implements AppSalesService {

    @Autowired
    AppSalesMapper appSalesMapper;

    @Override
    public List<ClientVO> getClientList() {
        return appSalesMapper.getClientList();
    }

    @Override
    public ClientVO getClientContent(int clientNo) {
        ClientVO vo = appSalesMapper.getClientContent(clientNo);
        return vo;
    }


    @Override
    public List<ContractVO> getProductList(int clientNo) {
        List<ContractVO> list = appSalesMapper.getProductList(clientNo);
        return list;
    }

    @Override
    public int getProductPrice(int clientNo, int productNo) {
        int result = appSalesMapper.getProductPrice(clientNo, productNo);
        return result;
    }

    @Override
    public int getInventoryQuantity(int productNo) {
        return appSalesMapper.getInventoryQuantity(productNo);
    }

    @Override
    public void orderRegist(OrderVO vo) {
        appSalesMapper.orderHeader(vo);
        appSalesMapper.orderItem(vo);
    }

    @Override
    public List<OrderVO> getList() {
        List<OrderVO> list = appSalesMapper.getList();
        return list;
    }

    @Override
    public List<OrderItemVO> getItems(int orderHeaderNo) {
        return appSalesMapper.getItems(orderHeaderNo);
    }
}
