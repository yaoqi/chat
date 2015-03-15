package com.mercury.chat.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageTemplate {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTemplate() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChatFromIsNull() {
            addCriterion("CHAT_FROM is null");
            return (Criteria) this;
        }

        public Criteria andChatFromIsNotNull() {
            addCriterion("CHAT_FROM is not null");
            return (Criteria) this;
        }

        public Criteria andChatFromEqualTo(String value) {
            addCriterion("CHAT_FROM =", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromNotEqualTo(String value) {
            addCriterion("CHAT_FROM <>", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromGreaterThan(String value) {
            addCriterion("CHAT_FROM >", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromGreaterThanOrEqualTo(String value) {
            addCriterion("CHAT_FROM >=", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromLessThan(String value) {
            addCriterion("CHAT_FROM <", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromLessThanOrEqualTo(String value) {
            addCriterion("CHAT_FROM <=", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromLike(String value) {
            addCriterion("CHAT_FROM like", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromNotLike(String value) {
            addCriterion("CHAT_FROM not like", value, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromIn(List<String> values) {
            addCriterion("CHAT_FROM in", values, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromNotIn(List<String> values) {
            addCriterion("CHAT_FROM not in", values, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromBetween(String value1, String value2) {
            addCriterion("CHAT_FROM between", value1, value2, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatFromNotBetween(String value1, String value2) {
            addCriterion("CHAT_FROM not between", value1, value2, "chatFrom");
            return (Criteria) this;
        }

        public Criteria andChatToIsNull() {
            addCriterion("CHAT_TO is null");
            return (Criteria) this;
        }

        public Criteria andChatToIsNotNull() {
            addCriterion("CHAT_TO is not null");
            return (Criteria) this;
        }

        public Criteria andChatToEqualTo(String value) {
            addCriterion("CHAT_TO =", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToNotEqualTo(String value) {
            addCriterion("CHAT_TO <>", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToGreaterThan(String value) {
            addCriterion("CHAT_TO >", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToGreaterThanOrEqualTo(String value) {
            addCriterion("CHAT_TO >=", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToLessThan(String value) {
            addCriterion("CHAT_TO <", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToLessThanOrEqualTo(String value) {
            addCriterion("CHAT_TO <=", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToLike(String value) {
            addCriterion("CHAT_TO like", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToNotLike(String value) {
            addCriterion("CHAT_TO not like", value, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToIn(List<String> values) {
            addCriterion("CHAT_TO in", values, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToNotIn(List<String> values) {
            addCriterion("CHAT_TO not in", values, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToBetween(String value1, String value2) {
            addCriterion("CHAT_TO between", value1, value2, "chatTo");
            return (Criteria) this;
        }

        public Criteria andChatToNotBetween(String value1, String value2) {
            addCriterion("CHAT_TO not between", value1, value2, "chatTo");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNull() {
            addCriterion("CREATE_TS is null");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNotNull() {
            addCriterion("CREATE_TS is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTsEqualTo(Date value) {
            addCriterion("CREATE_TS =", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotEqualTo(Date value) {
            addCriterion("CREATE_TS <>", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThan(Date value) {
            addCriterion("CREATE_TS >", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TS >=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThan(Date value) {
            addCriterion("CREATE_TS <", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TS <=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsIn(List<Date> values) {
            addCriterion("CREATE_TS in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotIn(List<Date> values) {
            addCriterion("CREATE_TS not in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsBetween(Date value1, Date value2) {
            addCriterion("CREATE_TS between", value1, value2, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TS not between", value1, value2, "createTs");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andChatFromLikeInsensitive(String value) {
            addCriterion("upper(CHAT_FROM) like", value.toUpperCase(), "chatFrom");
            return this;
        }

        public Criteria andChatToLikeInsensitive(String value) {
            addCriterion("upper(CHAT_TO) like", value.toUpperCase(), "chatTo");
            return this;
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}