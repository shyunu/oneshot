package com.project.oneshot.sales.contract;


import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;

import java.util.List;

public interface ContractService {
    // ----- 계약가격내역 ----- //
    public void contractRegist(List<ContractVO> list); //계약등록
    public List<ContractVO> getList(); //계약조회
    public List<ClientVO> getClientList(); //거래등록시 고객사명(번호) 조회
    public ClientVO getContractUpdateList(int clientNo);
    public List<ProductVO> getContractProductList(); //상품조회

    public Integer getContractPriceNo();


    public List<ContractVO> getContractDetails(int contractPriceNo);
}