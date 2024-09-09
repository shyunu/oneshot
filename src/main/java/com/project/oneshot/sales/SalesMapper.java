package com.project.oneshot.sales;

import com.project.oneshot.entity.ContractVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesMapper {
    public int contractRegist(ContractVO vo); //계약등록

}

