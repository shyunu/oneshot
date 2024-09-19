package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemVO {
    private Integer productNo;
    private Integer contractPrice;
    private Integer productQuantity;
    private Integer amount;
}
