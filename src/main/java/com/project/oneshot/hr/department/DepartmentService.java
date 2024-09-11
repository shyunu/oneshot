package com.project.oneshot.hr.department;


import com.project.oneshot.entity.mybatis.DepartmentVO;

import java.util.List;

public interface DepartmentService {
    public int insertDepartment(DepartmentVO vo);//등록
    public List<DepartmentVO> selectDepartment();

    public int deleteDepartments(List<Integer> departmentNos); // 삭제

}
