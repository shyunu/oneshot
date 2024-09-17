package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int orderRegist(OrderVO vo) {
        int result = orderMapper.orderRegist(vo);
        return result;
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

}
