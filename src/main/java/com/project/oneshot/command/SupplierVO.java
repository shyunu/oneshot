package com.project.oneshot.command;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupplierVO {

    private Long supplierNo;
    private String supplierName;
    private String supplierAddress;
    private String supplierBusinessNo;
    private String managerName;
    private String managerPhone;
    private String managerEmail;
    private String supplierFile;
}

