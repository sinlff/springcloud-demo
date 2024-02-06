package com.sinlff.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinlff.server.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper 数据访问接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}