package com.project.oneshot.sales.contract;


import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;

import java.util.List;

public interface ContractService {
    // ----- 계약가격내역 ----- //
    public int contractRegist(ContractVO vo); //계약등록
    public List<ContractVO> getList(); //계약조회
    public List<ClientVO> getClientList(); //거래등록시 고객사명(번호) 조회
    public ClientVO getContractUpdateList(int clientNo);
}