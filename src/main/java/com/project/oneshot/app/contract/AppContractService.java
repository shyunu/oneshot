package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;

import java.util.List;

public interface AppContractService {

    List<ClientVO> getClientList();

    List<ProductVO> getProductList();

    List<ContractVO> getContractList(int clientNo, int productNo);

    void registerContract(ContractVO vo);
}
