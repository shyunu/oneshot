package com.project.oneshot.command;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductVO {

    private Long productNo;
    private CategoryVO categoryVO;
    private SupplierVO supplierVO;
    private String productName;
    private String productContent;
    private Long safetyQuantity;
    private Long productPrice;
    private String productImg;
    private String productRemarks;

    private String categoryName;
    private String supplierName;
}
