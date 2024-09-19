package com.project.oneshot.common;

import com.project.oneshot.command.HomeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper {

    int login(HomeVO vo);

}
