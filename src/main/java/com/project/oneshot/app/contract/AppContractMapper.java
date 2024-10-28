package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AppContractMapper {

    List<ClientVO> getClientList();

    List<ProductVO> getProductList();

    List<ContractVO> getContractList(@Param("clientNo") int clientNo, @Param("productNo") int productNo);

    int countOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    List<ContractVO> getOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    void deleteContract(ContractVO existingContract);

    void updateContract(ContractVO existingContract);

    void getRegist(ContractVO vo);
}
