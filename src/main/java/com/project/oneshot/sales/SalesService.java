package com.project.oneshot.sales;


import com.project.oneshot.command.ContractVO;

import java.util.List;

public interface SalesService {
    public int contractRegist(ContractVO vo); //계약등록
    List<ContractVO> getList(); //계약조회

}