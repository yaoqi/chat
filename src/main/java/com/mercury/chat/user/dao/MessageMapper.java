package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.Message;
import com.mercury.chat.user.entity.MessageTemplate;
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

public interface MessageMapper {
    @SelectProvider(type=MessageSqlProvider.class, method="countByExample")
    int countByExample(MessageTemplate example);

    @DeleteProvider(type=MessageSqlProvider.class, method="deleteByExample")
    int deleteByExample(MessageTemplate example);

    @Delete({
        "delete from MESSAGE",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into MESSAGE (ID, CHAT_FROM, ",
        "CHAT_TO, CREATE_TS, ",
        "MESSAGE)",
        "values (#{id,jdbcType=BIGINT}, #{chatFrom,jdbcType=VARCHAR}, ",
        "#{chatTo,jdbcType=VARCHAR}, #{createTs,jdbcType=TIMESTAMP}, ",
        "#{message,jdbcType=CLOB})"
    })
    int insert(Message record);

    @InsertProvider(type=MessageSqlProvider.class, method="insertSelective")
    int insertSelective(Message record);

    @SelectProvider(type=MessageSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    List<Message> selectByExampleWithBLOBs(MessageTemplate example);

    @SelectProvider(type=MessageSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Message> selectByExample(MessageTemplate example);

    @Select({
        "select",
        "ID, CHAT_FROM, CHAT_TO, CREATE_TS, MESSAGE",
        "from MESSAGE",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    Message selectByPrimaryKey(Long id);

    @UpdateProvider(type=MessageSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageTemplate example);

    @UpdateProvider(type=MessageSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Message record, @Param("example") MessageTemplate example);

    @UpdateProvider(type=MessageSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Message record, @Param("example") MessageTemplate example);

    @UpdateProvider(type=MessageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Message record);

    @Update({
        "update MESSAGE",
        "set CHAT_FROM = #{chatFrom,jdbcType=VARCHAR},",
          "CHAT_TO = #{chatTo,jdbcType=VARCHAR},",
          "CREATE_TS = #{createTs,jdbcType=TIMESTAMP},",
          "MESSAGE = #{message,jdbcType=CLOB}",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Message record);

    @Update({
        "update MESSAGE",
        "set CHAT_FROM = #{chatFrom,jdbcType=VARCHAR},",
          "CHAT_TO = #{chatTo,jdbcType=VARCHAR},",
          "CREATE_TS = #{createTs,jdbcType=TIMESTAMP}",
        "where ID = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Message record);
}