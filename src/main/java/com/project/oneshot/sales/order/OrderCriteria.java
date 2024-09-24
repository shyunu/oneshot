package com.project.oneshot.sales.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderCriteria {
    //화면에 전달할 값들을 가지고 다니는 클래스
    private int page; //현재 조회하는 페이지
    private int amount; //조회하는 데이터개수

    //검색 키워드
    private String orderSdate; //판매등록일자
    private String clientName; //고객사명
    private String productName; //상품명
    private String employeeName; //판매담당자
    private String orderStatus; //판매승인상태
    private String deliveryStatus; //배송상태
    private String deliveryDate; //배송일

    public OrderCriteria() {
        this(1, 10);
    }
    public OrderCriteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }

    //offset - limit함수에 앞에 전달될 값
    public int getPageStart() {
        return (page - 1) * amount;
    }
}
