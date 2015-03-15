package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.User;
import com.mercury.chat.user.entity.UserTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
	
    @Select({
        "select",
        "UUID, USER_ID, PASSWORD",
        "from USER",
        "where USER_ID = #{{userId,jdbcType=VARCHAR} and PASSWORD = #{password,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="USER_ID", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    User select(String userId, String password);
	
    @SelectProvider(type=UserSqlProvider.class, method="countByExample")
    int countByExample(UserTemplate example);

    @DeleteProvider(type=UserSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserTemplate example);

    @Delete({
        "delete from USER",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long uuid);

    @Insert({
        "insert into USER (UUID, USER_ID, ",
        "PASSWORD)",
        "values (#{uuid,jdbcType=BIGINT}, #{userId,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @InsertProvider(type=UserSqlProvider.class, method="insertSelective")
    int insertSelective(User record);

    @SelectProvider(type=UserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="USER_ID", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectByExample(UserTemplate example);

    @Select({
        "select",
        "UUID, USER_ID, PASSWORD",
        "from USER",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="USER_ID", property="userId", jdbcType=JdbcType.VARCHAR),
        @Result(column="PASSWORD", property="password", jdbcType=JdbcType.VARCHAR)
    })
    User selectByPrimaryKey(Long uuid);
    
    @UpdateProvider(type=UserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserTemplate example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") User record, @Param("example") UserTemplate example);

    @UpdateProvider(type=UserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(User record);

    @Update({
        "update USER",
        "set USER_ID = #{userId,jdbcType=VARCHAR},",
          "PASSWORD = #{password,jdbcType=VARCHAR}",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);
}