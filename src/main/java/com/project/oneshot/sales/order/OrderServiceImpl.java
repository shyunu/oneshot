package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void orderRegist(OrderVO vo) {
        orderMapper.orderHeader(vo);
        orderMapper.orderItem(vo);
        orderMapper.changeProductStatus();
    }


    @Override
    public List<OrderVO> getList(OrderCriteria cri) {
        List<OrderVO> list = orderMapper.getList(cri);
        return list;
    }

    @Override
    public List<OrderItemVO> getItems(int orderHeaderNo) {
        return orderMapper.getItems(orderHeaderNo);
    }

    @Override
    public List<EmployeeVO> getEmployeeList() {
        List<EmployeeVO> list = orderMapper.getEmployeeList();
        return list;
    }

    @Override
    public EmployeeVO getEmployeeContent(int employeeNo) {
        EmployeeVO vo = orderMapper.getEmployeeContent(employeeNo);
        return vo;
    }

    @Override
    public List<OrderItemVO> getOrderItemsByOrderHeaderNo(int orderHeaderNo) {
        return orderMapper.getOrderItemsByOrderHeaderNo(orderHeaderNo);
    }

    @Override
    public int getOrderItemCount(int orderHeaderNo) {
        return orderMapper.getOrderItemCount(orderHeaderNo);
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

    @Override
    public int getProductPrice(int clientNo, int productNo) {
        int result = orderMapper.getProductPrice(clientNo, productNo);
        return result;
    }

//    @Override
//    public List<CategoryVO> getCategory(int productNo) {
//        List<CategoryVO> list = orderMapper.getCategory(productNo);
//        return list;
//    }

    @Override
    public int getTotalCount(OrderCriteria cri) {
        return orderMapper.getTotalCount(cri);
    }

    @Override
    public void updateDeliveryStatus(List<Integer> orderHeaderNos) {
        orderMapper.updateDeliveryStatus(orderHeaderNos);
    }

    @Override
    public void updateItem(OrderItemVO vo) {
        orderMapper.updateItem(vo);
    }

    @Override
    public int getInventoryQuantity(int productNo) {
        return orderMapper.getInventoryQuantity(productNo);
    }

}
