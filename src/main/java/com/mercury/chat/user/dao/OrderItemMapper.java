package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.OrderItem;
import com.mercury.chat.user.entity.OrderItemTemplate;
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

public interface OrderItemMapper {
    @SelectProvider(type=OrderItemSqlProvider.class, method="countByExample")
    int countByExample(OrderItemTemplate example);

    @DeleteProvider(type=OrderItemSqlProvider.class, method="deleteByExample")
    int deleteByExample(OrderItemTemplate example);

    @Delete({
        "delete from ORDER_ITEM",
        "where ITEM_ID = #{itemId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long itemId);

    @Insert({
        "insert into ORDER_ITEM (ITEM_ID, ORDER_ID, ",
        "PRODUCT_ID)",
        "values (#{itemId,jdbcType=BIGINT}, #{orderId,jdbcType=BIGINT}, ",
        "#{productId,jdbcType=BIGINT})"
    })
    int insert(OrderItem record);

    @InsertProvider(type=OrderItemSqlProvider.class, method="insertSelective")
    int insertSelective(OrderItem record);

    @SelectProvider(type=OrderItemSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="ITEM_ID", property="itemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT)
    })
    List<OrderItem> selectByExample(OrderItemTemplate example);

    @Select({
        "select",
        "ITEM_ID, ORDER_ID, PRODUCT_ID",
        "from ORDER_ITEM",
        "where ITEM_ID = #{itemId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="ITEM_ID", property="itemId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ORDER_ID", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT)
    })
    OrderItem selectByPrimaryKey(Long itemId);

    @UpdateProvider(type=OrderItemSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") OrderItem record, @Param("example") OrderItemTemplate example);

    @UpdateProvider(type=OrderItemSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") OrderItem record, @Param("example") OrderItemTemplate example);

    @UpdateProvider(type=OrderItemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrderItem record);

    @Update({
        "update ORDER_ITEM",
        "set ORDER_ID = #{orderId,jdbcType=BIGINT},",
          "PRODUCT_ID = #{productId,jdbcType=BIGINT}",
        "where ITEM_ID = #{itemId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OrderItem record);
}