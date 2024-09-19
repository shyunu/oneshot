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

        // 주문 아이템을 삽입
        orderMapper.orderItem(vo);
    }


    @Override
    public List<OrderVO> getList() {

        List<OrderVO> list = orderMapper.getList();

//        LocalDate currentDate = LocalDate.now();
//        for (OrderVO vo : list) {
//            LocalDate contractEdate = vo.getContractEdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //코드 분석 필요
//            long daysBetween = ChronoUnit.DAYS.between(contractEdate, currentDate);
//
//            if(daysBetween > 0 || daysBetween == 0) {
//                vo.setContractDday("계약만료");
//            } else {
//                vo.setContractDday("D" + daysBetween);
//            }
//        }

        return list;
    }

    @Override
    public List<OrderItemVO> getOrderItemsByHeaderNo(int orderHeaderNo) {
        return orderMapper.getOrderItemsByHeaderNo(orderHeaderNo);
    }

    @Override
    public void updateOrder(OrderVO orderVO) {
        orderMapper.updateOrder(orderVO);
    }

    @Override
    public List<EmployeeVO> getEmployeeList() {
        List<EmployeeVO> list = orderMapper.getEmployeeList();
        return list;
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

}
