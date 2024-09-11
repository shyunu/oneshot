package com.project.oneshot.sales;


import com.project.oneshot.entity.mybatis.ContractVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesMapper {
    public int contractRegist(ContractVO vo); //계약등록

    public List<ContractVO> getList();
}