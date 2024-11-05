package com.project.oneshot.app.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AppHomeServiceImpl implements AppHomeService {

    @Autowired
    AppHomeMapper appHomeMapper;

    @Override
    public int getContractCount() {
        return appHomeMapper.getContractCount();
    }

    @Override
    public int getSalesCount() {
        return appHomeMapper.getSalesCount();
    }

    //분기별 판매 총액 가져오기
    @Override
    public List<Map<String, Object>> getQuarterlyOrderAmount() {
        return appHomeMapper.getQuarterlyOrderAmount();
    }
}
