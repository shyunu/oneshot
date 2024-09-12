package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentVO {

    @NotNull(message = "부서번호는 필수입니다.")
    private Integer departmentNo;

    @NotBlank(message = "부서명은 필수입니다.")
    private String departmentName;
}