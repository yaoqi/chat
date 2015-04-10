package com.mercury.chat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.ProductSummaryTemplate;

public interface ProductSummaryMapper {
   
	@SelectProvider(type=ProductSummarySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="PRODUCT_CODE", property="productCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    List<ProductSummary> selectByExample(ProductSummaryTemplate example);

    @Select({
        "select",
        "PRODUCT_ID, PRODUCT_CODE, SUMMARY",
        "from CHAT.PRODUCT_SUMMARY",
        "where PRODUCT_ID = #{productId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="PRODUCT_CODE", property="productCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    ProductSummary selectByPrimaryKey(Long productId);

}