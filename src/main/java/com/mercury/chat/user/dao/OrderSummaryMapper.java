package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.OrderSummaryTemplate;
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

public interface OrderSummaryMapper {
    @SelectProvider(type=OrderSummarySqlProvider.class, method="countByExample")
    int countByExample(OrderSummaryTemplate example);

    @DeleteProvider(type=OrderSummarySqlProvider.class, method="deleteByExample")
    int deleteByExample(OrderSummaryTemplate example);

    @Delete({
        "delete from ORDER_SUMMARY",
        "where ORDER_ID = #{orderId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long orderId);

    @Insert({
        "insert into ORDER_SUMMARY (ORDER_ID, SUMMARY)",
        "values (#{orderId,jdbcType=BIGINT}, #{summary,jdbcType=VARCHAR})"
    })
    int insert(OrderSummary record);

    @InsertProvider(type=OrderSummarySqlProvider.class, method="insertSelective")
    int insertSelective(OrderSummary record);

    @SelectProvider(type=OrderSummarySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    List<OrderSummary> selectByExample(OrderSummaryTemplate example);

    @Select({
        "select",
        "ORDER_ID, SUMMARY",
        "from ORDER_SUMMARY",
        "where ORDER_ID = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    OrderSummary selectByPrimaryKey(Long orderId);

    @UpdateProvider(type=OrderSummarySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") OrderSummary record, @Param("example") OrderSummaryTemplate example);

    @UpdateProvider(type=OrderSummarySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") OrderSummary record, @Param("example") OrderSummaryTemplate example);

    @UpdateProvider(type=OrderSummarySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrderSummary record);

    @Update({
        "update ORDER_SUMMARY",
        "set SUMMARY = #{summary,jdbcType=VARCHAR}",
        "where ORDER_ID = #{orderId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OrderSummary record);
}