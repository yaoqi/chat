package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.QuickReply;
import com.mercury.chat.user.entity.QuickReplyTemplate;
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

public interface QuickReplyMapper {
	
    @SelectProvider(type=QuickReplySqlProvider.class, method="countByExample")
    int countByExample(QuickReplyTemplate example);

    @DeleteProvider(type=QuickReplySqlProvider.class, method="deleteByExample")
    int deleteByExample(QuickReplyTemplate example);

    @Delete({
        "delete from QUICK_REPLY",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long uuid);

    @Insert({
        "insert into QUICK_REPLY (UUID, SALEID, ",
        "MESSAGE)",
        "values (#{uuid,jdbcType=BIGINT}, #{saleid,jdbcType=BIGINT}, ",
        "#{message,jdbcType=CLOB})"
    })
    int insert(QuickReply record);

    @InsertProvider(type=QuickReplySqlProvider.class, method="insertSelective")
    int insertSelective(QuickReply record);

    @SelectProvider(type=QuickReplySqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="SALEID", property="saleid", jdbcType=JdbcType.BIGINT),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    List<QuickReply> selectByExampleWithBLOBs(QuickReplyTemplate example);

    @SelectProvider(type=QuickReplySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="SALEID", property="saleid", jdbcType=JdbcType.BIGINT)
    })
    List<QuickReply> selectByExample(QuickReplyTemplate example);

    @Select({
        "select",
        "UUID, SALEID, MESSAGE",
        "from QUICK_REPLY",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="UUID", property="uuid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="SALEID", property="saleid", jdbcType=JdbcType.BIGINT),
        @Result(column="MESSAGE", property="message", jdbcType=JdbcType.CLOB)
    })
    QuickReply selectByPrimaryKey(Long uuid);

    @UpdateProvider(type=QuickReplySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") QuickReply record, @Param("example") QuickReplyTemplate example);

    @UpdateProvider(type=QuickReplySqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") QuickReply record, @Param("example") QuickReplyTemplate example);

    @UpdateProvider(type=QuickReplySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") QuickReply record, @Param("example") QuickReplyTemplate example);

    @UpdateProvider(type=QuickReplySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(QuickReply record);

    @Update({
        "update QUICK_REPLY",
        "set SALEID = #{saleid,jdbcType=BIGINT},",
          "MESSAGE = #{message,jdbcType=CLOB}",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(QuickReply record);

    @Update({
        "update QUICK_REPLY",
        "set SALEID = #{saleid,jdbcType=BIGINT}",
        "where UUID = #{uuid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(QuickReply record);
}