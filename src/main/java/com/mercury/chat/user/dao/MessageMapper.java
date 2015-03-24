package com.mercury.chat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.MessageTemplate;

public interface MessageMapper {
    
	@SelectProvider(type=MessageSqlProvider.class, method="countByExample")
    int countByExample(MessageTemplate example);

    @InsertProvider(type=MessageSqlProvider.class, method="insertSelective")
    int insertSelective(ChatMessage record);

    @SelectProvider(type=MessageSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="SHOP_ID", property="shopId", jdbcType=JdbcType.BIGINT),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    List<ChatMessage> selectByExampleWithBLOBs(MessageTemplate example);
    
    @Select({
        "select",
        "ID, CHAT_FROM, CHAT_TO, SHOP_ID, CREATE_TS, MESSAGE",
        "from MESSAGE",
        "where (CHAT_FROM = #{userId,jdbcType=VARCHAR} or CHAT_TO = #{userId,jdbcType=VARCHAR})   and  SHOP_ID = #{shopId,jdbcType=BIGINT}",
        "limit #{batchSize} offset #{offset}"
    })
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="SHOP_ID", property="shopId", jdbcType=JdbcType.BIGINT),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    List<ChatMessage> select(@Param(value = "userId")String userId, @Param(value = "shopId")Long shopId, @Param(value = "offset")int offset, @Param(value = "batchSize")int batchSize);
	
    @Insert({
        "insert into MESSAGE (CHAT_FROM, ",
        "CHAT_TO, SHOP_ID, ",
        "MESSAGE)",
        "values (#{chatFrom,jdbcType=VARCHAR}, ",
        "#{chatTo,jdbcType=VARCHAR}, #{shopId,jdbcType=BIGINT}, ",
        "#{message,jdbcType=CLOB})"
    })
    int insert(ChatMessage record);
    
    @InsertProvider(type=MessageSqlProvider.class,method="insertAll")
    @Options
    int insertAll(List<ChatMessage> messages);
    
    @SelectProvider(type=MessageSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ID", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="CHAT_FROM", property="chatFrom", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHAT_TO", property="chatTo", jdbcType=JdbcType.VARCHAR),
        @Result(column="SHOP_ID", property="shopId", jdbcType=JdbcType.BIGINT),
        @Result(column="CREATE_TS", property="createTs", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ChatMessage> selectByExample(MessageTemplate example);


}