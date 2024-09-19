package com.project.oneshot.inventory.product;

import lombok.Data;

@Data //getter, setter
public class ProductCriteria {
    //화면에 전달할 값들을 가지고 다니는 클래스
    private int page; //현재 조회하는 페이지
    private int amount; //조회하는 데이터개수

    //검색 키워드
    private int categoryNo;
    private int supplierNo;
    private String productName;
    private String startProductPrice;
    private String endProductPrice;
    private String productRemark;
    //.........


    public ProductCriteria() {
       this(1, 10);
    }
    public ProductCriteria(int page, int amount) {
       this.page = page;
       this.amount = amount;
    }
    //offset - limit함수에 앞에 전달될 값
    public int getPageStart() {
        return (page - 1) * amount;
    }





}
