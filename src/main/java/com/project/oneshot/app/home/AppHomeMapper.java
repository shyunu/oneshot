package com.project.oneshot.app.home;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppHomeMapper {

    int getContractCount(); //금일 계약 등록건수 조회
    int getSalesCount(); //금일 판매 등록건수 조회

    List<Map<String, Object>> getQuarterlyOrderAmount(); //올해 분기별 매출액

}
