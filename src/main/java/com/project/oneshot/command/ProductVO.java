package com.project.oneshot.command;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVO {

    private int productNo;
    private int categoryNo;
    private int supplierNo;
    private String productName;
    private String productContent;
    private int inventoryQuantity;
    private int safetyQuantity;
    private int productPrice;
    private String productImg;
    private String productRemark;

    private String categoryName;
    private String supplierName;
    private String supplierBusinessNo;
    private String managerName;
    private String managerPhone;
}
