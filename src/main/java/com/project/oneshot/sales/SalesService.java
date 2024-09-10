package com.project.oneshot.sales;

import com.project.oneshot.vo.mybatis.ContractVO;


public interface SalesService {
    public int contractRegist(ContractVO vo); //계약등록
    public int contractUpdate(ContractVO vo);
}
