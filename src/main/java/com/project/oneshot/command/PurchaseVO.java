package com.project.oneshot.command;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseVO {

    private Integer purchaseListNo;
    private Integer productNo;
    private String productName;
    private Long categoryNo;
    private String categoryName;
    private Long supplierNo;
    private String supplierName;
    private Integer inventoryQuantity;
    private Integer safetyQuantity;
    private Integer purchasePrice;
    private Integer purchaseQuantity;
    private Integer employeeNo;
    private String employeeName;
    private String purchaseStatus;
    private Date purchaseDate;
}
