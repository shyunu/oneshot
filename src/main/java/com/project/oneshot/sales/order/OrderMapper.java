package com.project.oneshot.sales.order;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ContractVO;
import com.project.oneshot.command.OrderVO;
import com.project.oneshot.command.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    // ----- 판매내역 ----- //
    public int orderRegist(OrderVO vo);
    public List<ClientVO> getClientList();
    public ClientVO getClientContent(int clientNo);
    public List<ContractVO> getProductList(int clientNo);
}
