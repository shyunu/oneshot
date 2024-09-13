package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderVO {

    private Integer orderHeaderNo; //판매헤더번호
    private Date contractSdate; //계약시작일
    private Date contractEdate; //계약종료일
    private Date orderSdate; //판매등록일
    private Integer clientNo; //고객사번호
    private String orderStatus; //판매승인상태
    private String deliveryStatus; //배송상태
    private Date delivery; //배송일
    private Integer employeeNo; //계약담당자
    private Integer orderEmployee; //판매담당자

    private Integer productItemNo; //판매아이템번호
    private Integer productNo; //상품번호
    private Integer contractPrice; //거래가격
    private Integer productQuantity; //수량




}
