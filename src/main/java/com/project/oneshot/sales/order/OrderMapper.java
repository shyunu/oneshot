package com.project.oneshot.sales.order;

import com.project.oneshot.command.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    // ----- 판매내역 ----- //
    public int orderRegist(OrderVO vo); //판매등록

    public List<OrderVO> getList(); //판매내역조회

    public List<EmployeeVO> getEmployeeList(); //판매담당자 조회

    public List<ClientVO> getClientList(); //고객사조회
    public ClientVO getClientContent(int clientNo); //고객사 상세정보 조회
    public List<ContractVO> getProductList(int clientNo); //상품조회
}
