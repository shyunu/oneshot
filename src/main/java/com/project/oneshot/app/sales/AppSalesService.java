package com.project.oneshot.app.sales;

import com.project.oneshot.command.*;
import com.project.oneshot.sales.order.OrderCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppSalesService {
    List<ClientVO> getClientList(); //고객사리스트 조회
    ClientVO getClientContent(int clientNo); //고객사 상세정보 조회

    List<ContractVO> getProductList(int clientNo); //상품조회
    int getProductPrice(@Param("clientNo") int clientNo, @Param("productNo") int productNo); //계약가격조회
    int getInventoryQuantity(@Param("productNo") int productNo); //재고조회

    void orderRegist(OrderVO vo); //판매등록

    List<OrderVO> getList(); //판매내역조회
    List<OrderItemVO> getItems(@Param("orderHeaderNo") int orderHeaderNo); //상세조회


}
