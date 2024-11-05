package com.project.oneshot.app.home;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.OrderItemVO;
import com.project.oneshot.command.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppHomeServiceImpl implements AppHomeService {

    @Autowired
    AppHomeMapper appHomeMapper;

    @Override
    public int getContractCount() {
        return appHomeMapper.getContractCount();
    }

    @Override
    public int getSalesCount() {
        return appHomeMapper.getSalesCount();
    }
}
