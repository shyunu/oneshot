package com.project.oneshot.command;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryVO {

    private Long categoryNo;
    private String categoryName;
}
