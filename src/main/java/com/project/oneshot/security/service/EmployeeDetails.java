package com.project.oneshot.security.service;

import com.project.oneshot.command.EmployeeAuthVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeDetails implements UserDetails {

    //멤버변수 선언
    private EmployeeAuthVO employeeAuthVO;
    //객체생성
    public EmployeeDetails(EmployeeAuthVO employeeAuthVO) {
        this.employeeAuthVO = employeeAuthVO;
    }

    public int getPositionNo(){
        return employeeAuthVO.getPositionNo();
    }
    //로그인시 권한을 리턴해주는 함수
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();

        // departmentName과 positionName을 권한으로 설정
        list.add(new SimpleGrantedAuthority("ROLE_" + employeeAuthVO.getPositionNameEnglish()));
        for (Integer menu : employeeAuthVO.getMenuNo()) {
            list.add(new SimpleGrantedAuthority("ROLE_" + menu));
        }

        return list; // 권한 목록을 반환
    }

    //유저의 비밀번호 = 사원 생년월일 yymmdd
    @Override
    public String getPassword() {
        return employeeAuthVO.getEmployeePassword();
    }
    //유저의 아이디 = 사원번호
    @Override
    public String getUsername() {
        return String.valueOf(employeeAuthVO.getEmployeeNo());
    }

    public String getEmployeeStatus(){
        return employeeAuthVO.getEmployeeStatus();
    }
    // employeeName 반환
    public String getEmployeeName() {
        return employeeAuthVO.getEmployeeName();
    }
    // positionName 반환
    public String getPositionName() {
        return employeeAuthVO.getPositionName();
    }
    // positionName 반환
    public String getEmployeePhone() {return employeeAuthVO.getEmployeePhone();}

    // departmentNo 반환
    public int getDepartmentNo() {
        return employeeAuthVO.getDepartmentNo();
    }
    // departmentName 반환
    public String getDepartmentName() {
        return employeeAuthVO.getDepartmentName();
    }
    // employeePhotoPath 반환
    public String getEmployeePhotoPath(){
        return employeeAuthVO.getEmployeePhotoPath();
    }




    //계정이 만료되지 않았습니까? (true = 네)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정이 락이 걸리지 않았습니까?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //비밀번호 만료되지 않았습니까?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 사용할 수 있습니까?
    @Override
    public boolean isEnabled() {
        return true;
    }

}