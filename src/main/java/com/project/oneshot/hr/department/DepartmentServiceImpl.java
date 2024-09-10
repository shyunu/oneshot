package com.project.oneshot.hr.department;




import com.project.oneshot.entity.mybatis.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("humanResourceService") //이름
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper humanResourceMapper;


    @Override
    public int departmentInsert(DepartmentVO vo) {
        System.out.println(vo.getDepartmentName());
        int duplicateCount= humanResourceMapper.checkDuplicateDepartment(vo);
        if(duplicateCount>0){
            return 2;
        }else{
            int result = humanResourceMapper.departmentInsert(vo);
            return result;
        }
    }
}
