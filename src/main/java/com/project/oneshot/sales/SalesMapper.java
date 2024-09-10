package com.project.oneshot.sales;

import com.project.oneshot.vo.mybatis.ContractVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SalesMapper {
    public int contractRegist(ContractVO vo); //계약등록

}

