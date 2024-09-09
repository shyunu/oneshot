package com.project.oneshot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long productNo;

    @ManyToOne
    @JoinColumn(name = "categoryNo", insertable = false, updatable = false)
    private CategoryVO categoryVO;

    private Long categoryNo;
    private Long supplierNo;
    private String productName;
    private String productContent;
    private Long safetyQuantity;
    private Long productPrice;
    private String productImg;
    private String productRemarks;

    @Transient
    private String categoryName;

    public String getCategoryName() {
        if (categoryVO != null) {
            return categoryVO.getCategoryName();
        }
        return null;
    }
}
