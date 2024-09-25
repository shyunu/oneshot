package com.project.oneshot.security.service;

import com.project.oneshot.command.EmployeeAuthVO;
import com.project.oneshot.security.mapper.EmployeeAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeAuthMapper employeeAuthMapper;

    //loginProcessingUrl이 호출될때 자동으로 호출시킵니다.
    @Override
    public EmployeeDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("form에서 전달된 아이디:" + username);


        EmployeeAuthVO vo = employeeAuthMapper.login(Integer.parseInt(username));

        //로그인 성공
        if (vo != null) {
            List<Integer> menuList = employeeAuthMapper.getMenuForEmployee(Integer.parseInt(username));
            vo.setMenuNo(menuList);

            return new EmployeeDetails(vo);
        }


        throw new UsernameNotFoundException("아이디 및 비밀번호를 확인하세요");
    }

    public int insertEmployeeAuth(EmployeeAuthVO vo) {
        return employeeAuthMapper.insertEmployeeAuth(vo);
    }
    public int updateEmployeeAuth(EmployeeAuthVO vo) {return employeeAuthMapper.updateEmployeeAuth(vo);}

}