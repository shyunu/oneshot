package com.project.oneshot.app.contract;

import com.project.oneshot.command.AppContractVO;
import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AppContractMapper {

    List<ClientVO> getClientList();

    List<ProductVO> getProductList();

    List<AppContractVO> getContractPriceByClientNoAndProductNo(@Param("clientNo") int clientNo, @Param("productNo") int productNo);

    int countOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    List<AppContractVO> getOverlappingContracts(@Param("productNo") Integer productNo, @Param("clientNo") Integer clientNo, @Param("contractSdate") Date contractSdate, @Param("contractEdate") Date contractEdate, @Param("contractPriceStatus") String contractPriceStatus);

    void deleteContract(AppContractVO existingContract);

    void updateContract(AppContractVO existingContract);

    void registerContract(AppContractVO vo);

    List<AppContractVO> getAllContracts();

    List<AppContractVO> getContractPriceList(String search);

    String getContractFile(Integer contractPriceNo);
}
