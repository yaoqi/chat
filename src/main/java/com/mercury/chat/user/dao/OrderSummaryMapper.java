package com.mercury.chat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.mercury.chat.user.entity.OrderSummary;
import com.mercury.chat.user.entity.OrderSummaryTemplate;
import com.mercury.chat.user.entity.ProductSummary;

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
        "from CHAT.ORDER_SUMMARY",
        "where ORDER_ID = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(property="products", javaType=List.class, column="ORDER_ID", many=@Many(select="getProducts")),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)})
    OrderSummary selectByPrimaryKey(Long orderId);
    
    @Select({
        "select",
        "PRODUCT_SUMMARY.PRODUCT_ID, PRODUCT_SUMMARY.PRODUCT_CODE, PRODUCT_SUMMARY.SUMMARY",
        "from CHAT.PRODUCT_SUMMARY join CHAT.ORDER_ITEM on PRODUCT_SUMMARY.PRODUCT_ID = ORDER_ITEM.PRODUCT_ID",
        "where ORDER_ITEM.ORDER_ID = #{orderId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="PRODUCT_CODE", property="productCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    List<ProductSummary> getProducts(Long orderId);

}