package com.project.oneshot.app.contract;

import com.project.oneshot.command.AppContractVO;
import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;

import java.util.List;

public interface AppContractService {

    List<ClientVO> getClientList();

    List<ProductVO> getProductList();

    List<AppContractVO> getContractPriceByClientNoAndProductNo(int clientNo, int productNo);

    void registerContract(AppContractVO vo);

    List<AppContractVO> getAllContracts();

    List<AppContractVO> getContractPriceList(String search);

    String getContractFile(Integer contractPriceNo);
}
