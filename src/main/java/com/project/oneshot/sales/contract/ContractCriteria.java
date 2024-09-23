package com.project.oneshot.sales.contract;

import lombok.Data;

@Data
public class ContractCriteria {
    //화면에 전달할 값들을 가지고 다니는 클래스
    private int page; //현재 조회하는 페이지
    private int amount; //조회하는 데이터개수

    //검색 키워드
    private Integer clientNo;
    private String clientName;
    private String contractSdate; //판매등록일자
    private String contractEdate;
    private String managerName;
    private String productName; //상품명
    private String employeeName;
    private String contractPriceStatus; //판매승인상태

    public ContractCriteria() {
        this(1, 10);
    }
    public ContractCriteria(int page, int amount) {
        this.page = page;
        this.amount = amount;
    }

    //offset - limit함수에 앞에 전달될 값
    public int getPageStart() {
        return (page - 1) * amount;
    }
}
