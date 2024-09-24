package com.project.oneshot.hr.department;

import com.project.oneshot.command.DepartmentMenuVO;
import com.project.oneshot.command.DepartmentVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.MenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    // 부서 등록
    int insertDepartment(DepartmentVO vo);

    // 중복 확인
    int checkDuplicateDepartment(DepartmentVO vo);

    // 부서 목록 조회
    List<DepartmentVO> selectDepartment();

    // 부서 상태 업데이트
    int updateDepartmentState(@Param("departmentNo") int departmentNo, @Param("departmentState") String departmentState);

    // 부서 상세 정보 업데이트
    int updateDepartmentDetails(DepartmentVO department);

    // 부서와 메뉴 간 관계 추가
    int insertDepartmentMenu(@Param("departmentNo") int departmentNo, @Param("menuNo") int menuNo);

    // 부서의 기존 메뉴 삭제
    int deleteDepartmentMenus(@Param("departmentNo") int departmentNo);

    // 메뉴 목록 조회
    List<MenuVO> selectMenus();

    // 부서별 사원 목록 조회
    List<EmployeeVO> selectEmployeesByDepartment(@Param("departmentNo") int departmentNo);

    // 부서명 중복 확인
    int checkDuplicateDepartmentName(String departmentName);

    Integer getLastDepartmentNo();

    List<DepartmentMenuVO>getDepartmentMenus();


}