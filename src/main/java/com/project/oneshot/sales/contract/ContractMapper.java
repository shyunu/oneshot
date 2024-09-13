package com.project.oneshot.sales.contract;


import com.project.oneshot.command.ContractVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractMapper {
    // ----- 계약가격내역 ----- //
    public int contractRegist(ContractVO vo); //계약등록
    public List<ContractVO> getList();

}