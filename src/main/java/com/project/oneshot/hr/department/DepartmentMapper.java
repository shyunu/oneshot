package com.project.oneshot.hr.department;

import com.project.oneshot.command.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface DepartmentMapper {
    public int insertDepartment(DepartmentVO vo);//등록
    public int checkDuplicateDepartment(DepartmentVO vo);//같은번호가 있는지 확인
    List<DepartmentVO> selectDepartment();

    // 삭제
    int deleteDepartments(List<Integer> departmentNos);
}
