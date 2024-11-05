package com.project.oneshot.app.home;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.OrderItemVO;
import com.project.oneshot.command.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppHomeMapper {

    int getContractCount(); //금일 계약 등록건수 조회
    int getSalesCount(); //금일 판매 등록건수 조회

}
