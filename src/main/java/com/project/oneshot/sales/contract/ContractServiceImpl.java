package com.project.oneshot.sales.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public void contractRegist(List<ContractVO> list) {

         contractMapper.getRegist(list);
    }

    @Override
    public List<ContractVO> getList() {

        List<ContractVO> list = contractMapper.getList();

        LocalDate currentDate = LocalDate.now();
        for (ContractVO vo : list) {
            LocalDate contractEdate = vo.getContractEdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //코드 분석 필요
            long daysBetween = ChronoUnit.DAYS.between(contractEdate, currentDate);

            if(daysBetween > 0 || daysBetween == 0) {
                vo.setContractDday("계약만료");
            } else {
                vo.setContractDday("D" + daysBetween);
            }
        }

        return list;
    }

    @Override
    public List<ClientVO> getClientList() {

        List<ClientVO> list = contractMapper.getClientList();
        return list;
    }

    @Override
    public ClientVO getContractUpdateList(int clientNo) {

        ClientVO updateList = contractMapper.getContractUpdateList(clientNo);
        return updateList;
    }

    @Override
    public List<ProductVO> getContractProductList() {
        List<ProductVO> list = contractMapper.getContractProductList();
        return list;
    }


}