package com.mercury.chat.user.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.mercury.chat.user.entity.OrderItem;
import com.mercury.chat.user.entity.OrderItemTemplate.Criteria;
import com.mercury.chat.user.entity.OrderItemTemplate.Criterion;
import com.mercury.chat.user.entity.OrderItemTemplate;
import java.util.List;
import java.util.Map;

public class OrderItemSqlProvider {

    public String countByExample(OrderItemTemplate example) {
        BEGIN();
        SELECT("count(*)");
        FROM("CHAT.ORDER_ITEM");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(OrderItemTemplate example) {
        BEGIN();
        DELETE_FROM("CHAT.ORDER_ITEM");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(OrderItem record) {
        BEGIN();
        INSERT_INTO("CHAT.ORDER_ITEM");
        
        if (record.getItemId() != null) {
            VALUES("ITEM_ID", "#{itemId,jdbcType=BIGINT}");
        }
        
        if (record.getOrderId() != null) {
            VALUES("ORDER_ID", "#{orderId,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            VALUES("PRODUCT_ID", "#{productId,jdbcType=BIGINT}");
        }
        
        return SQL();
    }

    public String selectByExample(OrderItemTemplate example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("ITEM_ID");
        } else {
            SELECT("ITEM_ID");
        }
        SELECT("ORDER_ID");
        SELECT("PRODUCT_ID");
        FROM("CHAT.ORDER_ITEM");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        OrderItem record = (OrderItem) parameter.get("record");
        OrderItemTemplate example = (OrderItemTemplate) parameter.get("example");
        
        BEGIN();
        UPDATE("CHAT.ORDER_ITEM");
        
        if (record.getItemId() != null) {
            SET("ITEM_ID = #{record.itemId,jdbcType=BIGINT}");
        }
        
        if (record.getOrderId() != null) {
            SET("ORDER_ID = #{record.orderId,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            SET("PRODUCT_ID = #{record.productId,jdbcType=BIGINT}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("CHAT.ORDER_ITEM");
        
        SET("ITEM_ID = #{record.itemId,jdbcType=BIGINT}");
        SET("ORDER_ID = #{record.orderId,jdbcType=BIGINT}");
        SET("PRODUCT_ID = #{record.productId,jdbcType=BIGINT}");
        
        OrderItemTemplate example = (OrderItemTemplate) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(OrderItem record) {
        BEGIN();
        UPDATE("CHAT.ORDER_ITEM");
        
        if (record.getOrderId() != null) {
            SET("ORDER_ID = #{orderId,jdbcType=BIGINT}");
        }
        
        if (record.getProductId() != null) {
            SET("PRODUCT_ID = #{productId,jdbcType=BIGINT}");
        }
        
        WHERE("ITEM_ID = #{itemId,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(OrderItemTemplate example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}