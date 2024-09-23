package com.project.oneshot.sales.contract;


import com.project.oneshot.command.*;

import java.util.List;

public interface ContractService {
    // ----- 계약가격내역 ----- //
    public void contractRegist(List<ContractVO> list); //계약등록
    public List<ContractVO> getList(ContractCriteria cri); //계약조회
    public ClientVO getContractUpdateList(int clientNo);
    public List<ProductVO> getContractProductList(); //상품조회
    public Integer getContractPriceNo();
    public List<ContractVO> getContractDetails(int contractPriceNo);
    public void contractModify(ContractVO vo);
    public int getTotalCount(ContractCriteria cri);
    public List<ClientVO> getClientList(); //거래등록시 고객사명(번호) 조회
}