package com.project.oneshot.sales.contract;


import com.project.oneshot.command.ContractVO;

import java.util.List;

public interface ContractService {
    // ----- 계약가격내역 ----- //
    public int contractRegist(ContractVO vo); //계약등록
    public List<ContractVO> getList(); //계약조회

}