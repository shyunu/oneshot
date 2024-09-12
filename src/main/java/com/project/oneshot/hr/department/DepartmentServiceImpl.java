package com.project.oneshot.hr.department;

import com.project.oneshot.command.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("humanResourceService") //이름
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public int insertDepartment(DepartmentVO vo) {
        System.out.println(vo.getDepartmentName());
        int duplicateCount= departmentMapper.checkDuplicateDepartment(vo);
        if(duplicateCount>0){
            return 2;
        }else{
            int result = departmentMapper.insertDepartment(vo);
            return result;
        }
    }

    @Override
    public List<DepartmentVO> selectDepartment() {
        return departmentMapper.selectDepartment();
    }

    // 삭제
    @Override
    public int deleteDepartments(List<Integer> departmentNos) {
        return departmentMapper.deleteDepartments(departmentNos);
    }

}
