package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppContractServiceImpl implements AppContractService {

    @Autowired
    AppContractMapper appContractMapper;

    @Override
    public List<ClientVO> getClientList() {
        return appContractMapper.getClientList();
    }

    @Override
    public List<ProductVO> getProductList() {
        return appContractMapper.getProductList();
    }
}
