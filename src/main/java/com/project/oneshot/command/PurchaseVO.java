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
    private Integer purchasePrice;
    private Integer purchaseQuantity;
    private Date purchaseDate;
    private String purchaseStatus;
}
