package com.project.oneshot.humanResource;


import com.project.oneshot.entity.DepartmentVO;
import com.project.oneshot.entity.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("humanResourceService") //이름
public class HumanResourceServiceImpl implements HumanResourceService {

    @Autowired
    private HumanResourceMapper humanResourceMapper;


    @Override
    public int employeeInsert(EmployeeVO vo) {

        //1st - 상품 인서트
        int result = humanResourceMapper.employeeInsert(vo);


        return result;
    }

    @Override
    public int departmentInsert(DepartmentVO vo) {
        System.out.println(vo.getDepartmentName());
        int result = humanResourceMapper.departmentInsert(vo);

        return result;
    }
}
