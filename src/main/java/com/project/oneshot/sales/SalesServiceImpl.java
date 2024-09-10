package com.project.oneshot.sales;

import com.project.oneshot.vo.mybatis.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("salesService")
public class SalesServiceImpl implements SalesService{

    @Autowired
    private SalesMapper salesMapper;

    @Override
    public int contractRegist(ContractVO vo) { //계약등록
        int result = salesMapper.contractRegist(vo);
        return result;
    }

    @Override
    public int contractUpdate(ContractVO vo) { //계약수정
        return 0;
    }
}
