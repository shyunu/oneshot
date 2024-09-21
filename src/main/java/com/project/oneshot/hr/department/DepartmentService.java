package com.project.oneshot.hr.department;

import com.project.oneshot.command.DepartmentVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.MenuVO;

import java.util.List;

public interface DepartmentService {
    int insertDepartment(DepartmentVO vo); // 부서 등록, 선택된 메뉴 포함
    List<DepartmentVO> selectDepartment(); // 부서 조회
    boolean updateDepartmentState(int departmentNo, String departmentState); // 상태 업데이트
    boolean isDuplicateDepartment(int departmentNo, String departmentName); // 중복 확인
    List<EmployeeVO> getEmployeesByDepartment(int departmentNo); // 부서별 사원 목록 조회
    boolean updateDepartmentDetails(DepartmentVO department); // 부서 정보 및 메뉴 업데이트
    List<MenuVO> selectMenus(); // 메뉴 조회
}
