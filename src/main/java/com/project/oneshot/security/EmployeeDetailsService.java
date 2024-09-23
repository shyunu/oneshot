package com.project.oneshot.security;

import com.project.oneshot.command.EmployeeAuthVO;
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
            List<String> menuList = employeeAuthMapper.getMenuForEmployee(Integer.parseInt(username));
            vo.setMenuNameEnglish(menuList);

            return new EmployeeDetails(vo);
        }


        //유저명을 찾지 못하면 에러, login?error로 이동됩니다.
        //비밀번호가 틀리 더라도 에러, login?error로 이동됩니다.
        return null;
    }

    public int insertEmployeeAuth(EmployeeAuthVO vo) {
        return employeeAuthMapper.insertEmployeeAuth(vo);
    }
    public int updateEmployeeAuth(EmployeeAuthVO vo) {return employeeAuthMapper.updateEmployeeAuth(vo);}

}