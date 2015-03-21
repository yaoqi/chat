package com.mercury.chat.user.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.mercury.chat.user.entity.User;

public interface UserMapper {
	
    @Select({
        "select",
        "UUID, USER_ID, PASSWORD, SALES, SHOP_ID",
        "from USER",
        "where USER_ID = #{userId,jdbcType=VARCHAR} and  PASSWORD = #{password,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="USER_ID", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="SALES", property="sales", jdbcType=JdbcType.BOOLEAN),
        @Result(column="SHOP_ID", property="shopId", jdbcType=JdbcType.BIGINT)
    })
    User select(@Param(value = "userId")String userId, @Param(value = "password") String password);
}