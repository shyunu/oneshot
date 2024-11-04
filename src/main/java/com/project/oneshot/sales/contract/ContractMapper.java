package com.project.oneshot.sales.contract;


import com.project.oneshot.command.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ContractMapper {
    public void getRegist(ContractVO vo); //계약등록

    ContractVO getImageByContractPriceNo(ContractVO vo); //이미지조회

    public List<ContractVO> getList(ContractCriteria cri);

    public List<ClientVO> getClientList();

    public ClientVO getContractUpdateList(int clientNo);

    public List<ProductVO> getContractProductList();

    public Integer getContractPriceNo();

    public ContractVO getContractDetails(int contractPriceNo);

    public void getContractModify(ContractVO vo);

    public int getTotalCount(ContractCriteria cri);

    public void updateContract(ContractVO vo);

    public void deleteContract(ContractVO vo);

    int countOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    List<ContractVO> getOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    void updateOverlappingContract(@Param("productNo") Integer productNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("newEndDate") Date newEndDate);

    void insertContract(ContractVO splitContract);

    void approveContract(int contractPriceNo);

    void rejectContract(int contractPriceNo);

}