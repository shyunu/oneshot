package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientVO {

    private Integer clientNo; //고객사번호
    private String clientName; //고객사명
    private String clientBusinessNo; //고객사사업자등록번호
    private String clientAddress; //고객사주소
    private String managerName; //담당자명
    private String managerPhone; //담당자연락처
    private String managerEmail; //담당자 이메일
    private String clientFile; //첨부파일

}
