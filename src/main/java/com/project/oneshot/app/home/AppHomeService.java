package com.project.oneshot.app.home;

import java.util.List;
import java.util.Map;

public interface AppHomeService {

    int getContractCount(); //금일 계약 등록건수 조회
    int getSalesCount(); //금일 판매 등록건수 조회

    List<Map<String, Object>> getQuarterlyOrderAmount(); //올해 분기별 매출액

}
