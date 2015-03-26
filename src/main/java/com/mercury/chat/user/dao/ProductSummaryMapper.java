package com.mercury.chat.user.dao;

import com.mercury.chat.user.entity.ProductSummary;
import com.mercury.chat.user.entity.ProductSummaryTemplate;
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

public interface ProductSummaryMapper {
    @SelectProvider(type=ProductSummarySqlProvider.class, method="countByExample")
    int countByExample(ProductSummaryTemplate example);

    @DeleteProvider(type=ProductSummarySqlProvider.class, method="deleteByExample")
    int deleteByExample(ProductSummaryTemplate example);

    @Delete({
        "delete from PRODUCT_SUMMARY",
        "where PRODUCT_ID = #{productId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long productId);

    @Insert({
        "insert into PRODUCT_SUMMARY (PRODUCT_ID, PRODUCT_CODE, ",
        "SUMMARY)",
        "values (#{productId,jdbcType=BIGINT}, #{productCode,jdbcType=VARCHAR}, ",
        "#{summary,jdbcType=VARCHAR})"
    })
    int insert(ProductSummary record);

    @InsertProvider(type=ProductSummarySqlProvider.class, method="insertSelective")
    int insertSelective(ProductSummary record);

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
        "from PRODUCT_SUMMARY",
        "where PRODUCT_ID = #{productId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="PRODUCT_ID", property="productId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="PRODUCT_CODE", property="productCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="SUMMARY", property="summary", jdbcType=JdbcType.VARCHAR)
    })
    ProductSummary selectByPrimaryKey(Long productId);

    @UpdateProvider(type=ProductSummarySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ProductSummary record, @Param("example") ProductSummaryTemplate example);

    @UpdateProvider(type=ProductSummarySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ProductSummary record, @Param("example") ProductSummaryTemplate example);

    @UpdateProvider(type=ProductSummarySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ProductSummary record);

    @Update({
        "update PRODUCT_SUMMARY",
        "set PRODUCT_CODE = #{productCode,jdbcType=VARCHAR},",
          "SUMMARY = #{summary,jdbcType=VARCHAR}",
        "where PRODUCT_ID = #{productId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ProductSummary record);
}