package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.product.ProductCriteria;

import java.util.List;

public interface OrderService {

    public void orderRegist(OrderVO vo); //판매등록

    public List<OrderVO> getList(OrderCriteria cri); //판매내역조회(메인페이지)
    public List<OrderItemVO> getItems(int orderHeaderNo);

    public List<EmployeeVO> getEmployeeList(); //판매담당자 조회

    public List<OrderItemVO> getOrderItemsByOrderHeaderNo(int orderHeaderNo);
    public int getOrderItemCount(int orderHeaderNo);

    public List<ClientVO> getClientList(); //고객사조회
    public ClientVO getClientContent(int clientNo); //고객사 상세정보 조회
    public List<ContractVO> getProductList(int clientNo); //상품조회
    public int getProductPrice(int clientNo, int productNo);

    public int getTotalCount(OrderCriteria cri);

}
