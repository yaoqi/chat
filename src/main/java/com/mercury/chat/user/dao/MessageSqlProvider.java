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

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.mercury.chat.user.entity.ChatMessage;
import com.mercury.chat.user.entity.MessageTemplate;
import com.mercury.chat.user.entity.MessageTemplate.Criteria;
import com.mercury.chat.user.entity.MessageTemplate.Criterion;

public class MessageSqlProvider {

    public String countByExample(MessageTemplate example) {
        BEGIN();
        SELECT("count (1)");
        FROM("MESSAGE");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(MessageTemplate example) {
        BEGIN();
        DELETE_FROM("MESSAGE");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(ChatMessage record) {
        BEGIN();
        INSERT_INTO("MESSAGE");
        
        if (record.getId() != null) {
            VALUES("ID", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getChatFrom() != null) {
            VALUES("CHAT_FROM", "#{chatFrom,jdbcType=VARCHAR}");
        }
        
        if (record.getChatTo() != null) {
            VALUES("CHAT_TO", "#{chatTo,jdbcType=VARCHAR}");
        }
        
        if (record.getShopId() != null) {
            VALUES("SHOP_ID", "#{shopId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTs() != null) {
            VALUES("CREATE_TS", "#{createTs,jdbcType=TIMESTAMP}");
        }
        
        if (record.getMessage() != null) {
            VALUES("MESSAGE", "#{message,jdbcType=CLOB}");
        }
        
        return SQL();
    }
    
   public String insertAll(Map<String,List<ChatMessage>> map) {      
       List<ChatMessage> users = map.get("list");   
       StringBuilder sb = new StringBuilder();   
       sb.append("INSERT INTO MESSAGE (CHAT_FROM,CHAT_TO,SHOP_ID,MESSAGE) VALUES");   
       MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].chatFrom},#'{'list[{0}].chatTo},#'{'list[{0}].shopId},#'{'list[{0}].message})");   
       for(int i = 0 ;i<users.size();i++) {   
           sb.append(messageFormat.format(new Object[]{i}));   
           if (i < users.size() - 1) {     
               sb.append(",");      
           }   
       }  
       return sb.toString();   
   } 

    public String selectByExampleWithBLOBs(MessageTemplate example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("ID");
        } else {
            SELECT("ID");
        }
        SELECT("CHAT_FROM");
        SELECT("CHAT_TO");
        SELECT("SHOP_ID");
        SELECT("CREATE_TS");
        SELECT("MESSAGE");
        FROM("MESSAGE");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String selectByExample(MessageTemplate example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("ID");
        } else {
            SELECT("ID");
        }
        SELECT("CHAT_FROM");
        SELECT("CHAT_TO");
        SELECT("SHOP_ID");
        SELECT("CREATE_TS");
        FROM("MESSAGE");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        ChatMessage record = (ChatMessage) parameter.get("record");
        MessageTemplate example = (MessageTemplate) parameter.get("example");
        
        BEGIN();
        UPDATE("MESSAGE");
        
        if (record.getId() != null) {
            SET("ID = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getChatFrom() != null) {
            SET("CHAT_FROM = #{record.chatFrom,jdbcType=VARCHAR}");
        }
        
        if (record.getChatTo() != null) {
            SET("CHAT_TO = #{record.chatTo,jdbcType=VARCHAR}");
        }
        
        if (record.getShopId() != null) {
            SET("SHOP_ID = #{record.shopId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTs() != null) {
            SET("CREATE_TS = #{record.createTs,jdbcType=TIMESTAMP}");
        }
        
        if (record.getMessage() != null) {
            SET("MESSAGE = #{record.message,jdbcType=CLOB}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("MESSAGE");
        
        SET("ID = #{record.id,jdbcType=BIGINT}");
        SET("CHAT_FROM = #{record.chatFrom,jdbcType=VARCHAR}");
        SET("CHAT_TO = #{record.chatTo,jdbcType=VARCHAR}");
        SET("SHOP_ID = #{record.shopId,jdbcType=BIGINT}");
        SET("CREATE_TS = #{record.createTs,jdbcType=TIMESTAMP}");
        SET("MESSAGE = #{record.message,jdbcType=CLOB}");
        
        MessageTemplate example = (MessageTemplate) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("MESSAGE");
        
        SET("ID = #{record.id,jdbcType=BIGINT}");
        SET("CHAT_FROM = #{record.chatFrom,jdbcType=VARCHAR}");
        SET("CHAT_TO = #{record.chatTo,jdbcType=VARCHAR}");
        SET("SHOP_ID = #{record.shopId,jdbcType=BIGINT}");
        SET("CREATE_TS = #{record.createTs,jdbcType=TIMESTAMP}");
        
        MessageTemplate example = (MessageTemplate) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(ChatMessage record) {
        BEGIN();
        UPDATE("MESSAGE");
        
        if (record.getChatFrom() != null) {
            SET("CHAT_FROM = #{chatFrom,jdbcType=VARCHAR}");
        }
        
        if (record.getChatTo() != null) {
            SET("CHAT_TO = #{chatTo,jdbcType=VARCHAR}");
        }
        
        if (record.getShopId() != null) {
            SET("SHOP_ID = #{shopId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTs() != null) {
            SET("CREATE_TS = #{createTs,jdbcType=TIMESTAMP}");
        }
        
        if (record.getMessage() != null) {
            SET("MESSAGE = #{message,jdbcType=CLOB}");
        }
        
        WHERE("ID = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(MessageTemplate example, boolean includeExamplePhrase) {
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