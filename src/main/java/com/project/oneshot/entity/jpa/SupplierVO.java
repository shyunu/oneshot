package com.project.oneshot.entity.jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
    @SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
    private Long supplierNo;
    private String supplierName;
    private String supplierAddress;
    private String supplierBusinessNo;
    private String managerName;
    private String managerPhone;
    private String managerEmail;
    private String supplierFile;
}

