package com.project.oneshot.humanResource;


import com.project.oneshot.entity.DepartmentVO;
import com.project.oneshot.entity.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HumanResourceMapper {
    public int employeeInsert(EmployeeVO vo);//등록



    public int departmentInsert(DepartmentVO vo);//등록

}
