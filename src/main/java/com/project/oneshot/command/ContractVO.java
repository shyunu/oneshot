package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContractVO { //계약가격 테이블(DB기준)
    private Integer productNo; //상품번호
    private Integer employeeNo; //사원번호
    private Integer clientNo; //고객사번호
    private Date contractSdate; //계약시작일
    private Date contractEdate; //계약종료일
    private BigDecimal contractPrice; //책정거래가
    private byte[] contractFile;
    private String base64Image;

    private MultipartFile contractFile; //계약서
    private byte[] contractFileData; // 파일 데이터 (byte[]로 저장)

    private Integer contractPriceNo;
    private String clientName;
    private String productName;
    private String managerName;
    private String contractPriceStatus;
    private String contractDday;
    private String employeeName;

    private String managerPhone;
    private String employeePhone;

    private List<ContractItemVO> contractItems;
}