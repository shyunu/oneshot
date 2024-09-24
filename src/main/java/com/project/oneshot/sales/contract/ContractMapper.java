package com.project.oneshot.sales.contract;


import com.project.oneshot.command.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ContractMapper {
    public void getRegist(List<ContractVO> list); //계약등록
    public int contractItem(ContractVO vo);
    public List<ContractVO> getList(ContractCriteria cri);
    public List<ClientVO> getClientList();
    public ClientVO getContractUpdateList(int clientNo);
    public List<ProductVO> getContractProductList();
    public Integer getContractPriceNo();
    public List<ContractVO> getContractDetails(int contractPriceNo);
    public void getContractModify(ContractVO vo);
    public int getTotalCount(ContractCriteria cri);

    public List<ContractVO> updateContract(int clientNo, int productNo, String contractSdate, Date contractEdate);

    public int updateContractDate(int clientNo, int productNo, Date contractSdate, Date contractEdate);


}