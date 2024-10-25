package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import com.project.oneshot.inventory.product.ProductCriteria;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public void orderRegist(OrderVO vo); //판매등록

    public List<OrderVO> getList(OrderCriteria cri); //판매내역조회(메인페이지)
    public List<OrderItemVO> getItems(int orderHeaderNo);


    public List<OrderItemVO> getOrderItemsByOrderHeaderNo(int orderHeaderNo);
    public int getOrderItemCount(int orderHeaderNo);

    public List<EmployeeVO> getEmployeeList(); //판매담당자 조회
    public EmployeeVO getEmployeeContent(int employeeNo); //사원 상세정보 조회
    public List<ClientVO> getClientList(); //고객사조회
    public ClientVO getClientContent(int clientNo); //고객사 상세정보 조회
    public List<ContractVO> getProductList(int clientNo); //상품조회
    public int getProductPrice(int clientNo, int productNo); //계약가격조회
//    public List<CategoryVO> getCategory(int productNo); //카테고리 조회

    public int getTotalCount(OrderCriteria cri);
    public void updateDeliveryStatus(List<Integer> orderHeaderNos); //업데이트(상태)
    public void updateItem(OrderItemVO vo); //업데이트(상품정보)

    int getInventoryQuantity(int productNo); //재고조회

    // 분기별 판매 총액 조회
    List<Map<String, Object>> getQuarterlyOrderAmount();
}
