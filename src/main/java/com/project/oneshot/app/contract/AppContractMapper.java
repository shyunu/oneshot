package com.project.oneshot.app.contract;

import com.project.oneshot.command.ClientVO;
import com.project.oneshot.command.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppContractMapper {

    List<ClientVO> getClientList();

    List<ProductVO> getProductList();
}
