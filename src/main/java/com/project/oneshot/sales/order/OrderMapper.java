package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    // ----- 판매내역 ----- //
    public int orderHeader(OrderVO vo); //판매등록
    public int orderItem(OrderVO vo); //판매상세등록

    public List<OrderVO> getList(); //판매내역조회
    public List<OrderItemVO> getOrderItemsByHeaderNo(int orderHeaderNo); //판매내역의 판매아이템 리스트 조회(상세페이지)
    public void updateOrder(OrderVO orderVO); //판매내역 수정사항 반영

    public List<EmployeeVO> getEmployeeList(); //판매담당자 조회

    public List<ClientVO> getClientList(); //고객사조회
    public ClientVO getClientContent(int clientNo); //고객사 상세정보 조회
    public List<ContractVO> getProductList(int clientNo); //상품조회
    public int getProductPrice(@Param("clientNo") int clientNo, @Param("productNo") int productNo);
}
