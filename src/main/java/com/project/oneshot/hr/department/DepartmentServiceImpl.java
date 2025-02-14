package com.project.oneshot.hr.department;

import com.project.oneshot.command.DepartmentMenuVO;
import com.project.oneshot.command.DepartmentVO;
import com.project.oneshot.command.EmployeeVO;
import com.project.oneshot.command.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public int insertDepartment(DepartmentVO vo) {
        // 중복 확인
        int duplicateCount = departmentMapper.checkDuplicateDepartment(vo);
        if (duplicateCount > 0) {
            return 2;  // 중복 시 처리
        }

        // 부서 등록
        departmentMapper.insertDepartment(vo);

        // 선택된 메뉴 등록
        if (vo.getMenuNo() != null && !vo.getMenuNo().isEmpty()) {
            for (Integer menuNo : vo.getMenuNo()) {
                departmentMapper.insertDepartmentMenu(vo.getDepartmentNo(), menuNo); // 메뉴 추가
            }
        }
        return 1; // 성공 시 처리
    }

    @Override
    public boolean isDuplicateDepartment(int departmentNo, String departmentName) {
        DepartmentVO departmentVO = DepartmentVO.builder()
                .departmentNo(departmentNo)
                .departmentName(departmentName)
                .build();
        return departmentMapper.checkDuplicateDepartment(departmentVO) > 0;
    }

    @Override
    public List<DepartmentVO> selectDepartment() {
        return departmentMapper.selectDepartment();
    }

    @Override
    public List<DepartmentVO> getActiveDepartments() {
        return departmentMapper.getActiveDepartments();
    }

    @Override
    public List<Map<String, Object>> countDeptPosEmployees() {
        return departmentMapper.countDeptPosEmployees();
    }

    @Override
    public boolean updateDepartmentState(int departmentNo, String departmentState) {
        return departmentMapper.updateDepartmentState(departmentNo, departmentState) > 0;
    }

    @Override
    public List<EmployeeVO> getEmployeesByDepartment(int departmentNo) {
        return departmentMapper.selectEmployeesByDepartment(departmentNo);
    }

    @Override
    public boolean updateDepartmentDetails(DepartmentVO department) {
        // 부서명과 상태 업데이트
        departmentMapper.updateDepartmentDetails(department);

        departmentMapper.deleteDepartmentMenus(department.getDepartmentNo());
        if (department.getMenuNo() != null && !department.getMenuNo().isEmpty()) {
            // 메뉴가 존재하는지 체크 후 관계 업데이트
            for (Integer menuNo : department.getMenuNo()) {
                departmentMapper.insertDepartmentMenu(department.getDepartmentNo(), menuNo);
            }
        }

        return true;
    }

    @Override
    public List<MenuVO> selectMenus() {
        return departmentMapper.selectMenus();
    }

    // 부서명 중복
    @Override
    public boolean isDuplicateDepartmentName(String departmentName) {
        return departmentMapper.checkDuplicateDepartmentName(departmentName) > 0;
    }

    // 부서번호 자동
    @Override
    public Integer getLastDepartmentNo() {
        return departmentMapper.getLastDepartmentNo();
    }
    
    //부서별 사용가능 메뉴 불러오기
    @Override
    public List<DepartmentMenuVO> getDepartmentMenus() {
        return departmentMapper.getDepartmentMenus();
    }
}