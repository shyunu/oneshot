package com.project.oneshot.command;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryVO {

    private Long categoryNo;
    private String categoryName;
}
