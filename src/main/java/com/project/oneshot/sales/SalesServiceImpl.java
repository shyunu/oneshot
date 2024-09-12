package com.project.oneshot.sales;

import com.project.oneshot.command.ContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service("salesService")
public class SalesServiceImpl implements SalesService{

    @Autowired
    private SalesMapper salesMapper;

    @Override
    public int contractRegist(ContractVO vo) { //계약등록
        int result = salesMapper.contractRegist(vo);
        return result;
    }

    @Override
    public List<ContractVO> getList() {


        List<ContractVO> list = salesMapper.getList();

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
}