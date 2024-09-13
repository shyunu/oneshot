package com.project.oneshot.common;

import com.project.oneshot.command.HomeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

    @Autowired
    private HomeMapper homeMapper;


    @Override
    public int login(HomeVO vo) {

        return homeMapper.login(vo);
    }
}
