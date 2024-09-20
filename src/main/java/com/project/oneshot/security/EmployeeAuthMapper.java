package com.project.oneshot.security;

import com.project.oneshot.command.EmployeeAuthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeAuthMapper {
    //비밀번호 넣기
    public int insertEmployeeAuth(EmployeeAuthVO vo);

    //로그인
    public EmployeeAuthVO login(int username);
    //사용가능 메뉴 가져오기
    public List<String> getMenuForEmployee(int username);
}
