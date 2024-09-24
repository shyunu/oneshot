package com.project.oneshot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentVO {

    @NotNull(message = "부서번호는 필수입니다.")
    private Integer departmentNo;

    @NotBlank(message = "부서명은 필수입니다.")
    private String departmentName;

    @Builder.Default
    private String departmentState = "Y"; // 활성 기본값 Y

    // 선택된 메뉴 번호 리스트
    private List<Integer> menuNo;

}
