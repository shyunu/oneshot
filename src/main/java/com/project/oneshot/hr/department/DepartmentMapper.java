package com.project.oneshot.hr.department;




import com.project.oneshot.entity.mybatis.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DepartmentMapper {
    public int departmentInsert(DepartmentVO vo);//등록
    public int checkDuplicateDepartment(DepartmentVO vo);//같은번호가 있는지 확인

}
