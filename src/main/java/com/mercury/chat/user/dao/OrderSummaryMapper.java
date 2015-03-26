package com.mercury.chat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.OrderSummaryTemplate;

public interface OrderSummaryMapper {

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

}