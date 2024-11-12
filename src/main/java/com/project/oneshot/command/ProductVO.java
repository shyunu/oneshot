package com.project.oneshot.command;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVO {

    private Integer productNo;
    private Integer categoryNo;
    private Integer supplierNo;
    private String productName;
    private String productContent;
    private Integer inventoryQuantity;
    private Integer safetyQuantity;
    private Integer productPrice;
    private String productImg;
    private byte[] productImgApp;  // String에서 byte[]로 수정
    private String productRemark;

    private String categoryName;
    private String supplierName;
    private String supplierBusinessNo;
    private String managerName;
    private String managerPhone;

    public byte[] getProductImgApp() {
        return productImgApp;
    }

    public void setProductImgApp(byte[] productImgApp) {
        this.productImgApp = productImgApp;
    }
}
