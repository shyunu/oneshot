package com.project.oneshot.sales;

import com.project.oneshot.entity.ContractVO;
import org.springframework.stereotype.Service;


public interface SalesService {
    public int contractRegist(ContractVO vo); //계약등록
    public int contractUpdate(ContractVO vo);
}
