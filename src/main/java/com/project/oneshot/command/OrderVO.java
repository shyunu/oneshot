package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderVO {

    private Integer orderHeaderNo; //판매헤더번호
    private Date orderSdate; //판매등록일
    private Integer clientNo; //고객사번호
    private String clientName; // 고객명
    private String orderStatus; //판매승인상태
    private String deliveryStatus; //배송상태
    private Date delivery; //배송일
    private Integer employeeNo; //판매담당자번호
    private String employeeName; //판매담당자이름
    private Integer totalAmount; // 판매건별 총액
    private String productNames; // 상품명 목록(조회에서볼내용)

    private List<OrderItemVO> orderItems; //판매하는 상품을 묶는 배열
}
