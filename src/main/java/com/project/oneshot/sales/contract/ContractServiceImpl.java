package com.project.oneshot.sales.contract;

import com.project.oneshot.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    public List<ContractVO> getList(ContractCriteria cri) {

        List<ContractVO> list = contractMapper.getList(cri);

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

    @Override
    public Integer getContractPriceNo() {
        return contractMapper.getContractPriceNo();
    }

    @Override
    public List<ContractVO> getContractDetails(int contractPriceNo) {
        return contractMapper.getContractDetails(contractPriceNo);
    }

    @Override
    public void contractModify(ContractVO vo) {
        System.out.println("ContractServiceImpl.contractModify");
        System.out.println("vo = " + vo);
        contractMapper.getContractModify(vo);
    }

    @Override
    public int getTotalCount(ContractCriteria cri) {
        return contractMapper.getTotalCount(cri);
    }

    @Override
    public List<ContractVO> updateContract(int clientNo, int productNo, String contractSdate, Date contractEdate) {
        return contractMapper.updateContract(clientNo, productNo, contractSdate, contractEdate);
    }

    @Override
    public int updateContractDate(int clientNo, int productNo, Date contractSdate, Date contractEdate) {
        return contractMapper.updateContractDate(clientNo, productNo, contractSdate, contractEdate);
    }

}