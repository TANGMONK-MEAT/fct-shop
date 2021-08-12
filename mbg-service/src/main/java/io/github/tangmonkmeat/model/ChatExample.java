package io.github.tangmonkmeat.model;

import java.util.ArrayList;
import java.util.List;

public class ChatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChatExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andU1IsNull() {
            addCriterion("u1 is null");
            return (Criteria) this;
        }

        public Criteria andU1IsNotNull() {
            addCriterion("u1 is not null");
            return (Criteria) this;
        }

        public Criteria andU1EqualTo(String value) {
            addCriterion("u1 =", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1NotEqualTo(String value) {
            addCriterion("u1 <>", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1GreaterThan(String value) {
            addCriterion("u1 >", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1GreaterThanOrEqualTo(String value) {
            addCriterion("u1 >=", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1LessThan(String value) {
            addCriterion("u1 <", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1LessThanOrEqualTo(String value) {
            addCriterion("u1 <=", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1Like(String value) {
            addCriterion("u1 like", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1NotLike(String value) {
            addCriterion("u1 not like", value, "u1");
            return (Criteria) this;
        }

        public Criteria andU1In(List<String> values) {
            addCriterion("u1 in", values, "u1");
            return (Criteria) this;
        }

        public Criteria andU1NotIn(List<String> values) {
            addCriterion("u1 not in", values, "u1");
            return (Criteria) this;
        }

        public Criteria andU1Between(String value1, String value2) {
            addCriterion("u1 between", value1, value2, "u1");
            return (Criteria) this;
        }

        public Criteria andU1NotBetween(String value1, String value2) {
            addCriterion("u1 not between", value1, value2, "u1");
            return (Criteria) this;
        }

        public Criteria andU2IsNull() {
            addCriterion("u2 is null");
            return (Criteria) this;
        }

        public Criteria andU2IsNotNull() {
            addCriterion("u2 is not null");
            return (Criteria) this;
        }

        public Criteria andU2EqualTo(String value) {
            addCriterion("u2 =", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2NotEqualTo(String value) {
            addCriterion("u2 <>", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2GreaterThan(String value) {
            addCriterion("u2 >", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2GreaterThanOrEqualTo(String value) {
            addCriterion("u2 >=", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2LessThan(String value) {
            addCriterion("u2 <", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2LessThanOrEqualTo(String value) {
            addCriterion("u2 <=", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2Like(String value) {
            addCriterion("u2 like", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2NotLike(String value) {
            addCriterion("u2 not like", value, "u2");
            return (Criteria) this;
        }

        public Criteria andU2In(List<String> values) {
            addCriterion("u2 in", values, "u2");
            return (Criteria) this;
        }

        public Criteria andU2NotIn(List<String> values) {
            addCriterion("u2 not in", values, "u2");
            return (Criteria) this;
        }

        public Criteria andU2Between(String value1, String value2) {
            addCriterion("u2 between", value1, value2, "u2");
            return (Criteria) this;
        }

        public Criteria andU2NotBetween(String value1, String value2) {
            addCriterion("u2 not between", value1, value2, "u2");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andShowToU1IsNull() {
            addCriterion("show_to_u1 is null");
            return (Criteria) this;
        }

        public Criteria andShowToU1IsNotNull() {
            addCriterion("show_to_u1 is not null");
            return (Criteria) this;
        }

        public Criteria andShowToU1EqualTo(Boolean value) {
            addCriterion("show_to_u1 =", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1NotEqualTo(Boolean value) {
            addCriterion("show_to_u1 <>", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1GreaterThan(Boolean value) {
            addCriterion("show_to_u1 >", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1GreaterThanOrEqualTo(Boolean value) {
            addCriterion("show_to_u1 >=", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1LessThan(Boolean value) {
            addCriterion("show_to_u1 <", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1LessThanOrEqualTo(Boolean value) {
            addCriterion("show_to_u1 <=", value, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1In(List<Boolean> values) {
            addCriterion("show_to_u1 in", values, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1NotIn(List<Boolean> values) {
            addCriterion("show_to_u1 not in", values, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1Between(Boolean value1, Boolean value2) {
            addCriterion("show_to_u1 between", value1, value2, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU1NotBetween(Boolean value1, Boolean value2) {
            addCriterion("show_to_u1 not between", value1, value2, "showToU1");
            return (Criteria) this;
        }

        public Criteria andShowToU2IsNull() {
            addCriterion("show_to_u2 is null");
            return (Criteria) this;
        }

        public Criteria andShowToU2IsNotNull() {
            addCriterion("show_to_u2 is not null");
            return (Criteria) this;
        }

        public Criteria andShowToU2EqualTo(Boolean value) {
            addCriterion("show_to_u2 =", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2NotEqualTo(Boolean value) {
            addCriterion("show_to_u2 <>", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2GreaterThan(Boolean value) {
            addCriterion("show_to_u2 >", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2GreaterThanOrEqualTo(Boolean value) {
            addCriterion("show_to_u2 >=", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2LessThan(Boolean value) {
            addCriterion("show_to_u2 <", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2LessThanOrEqualTo(Boolean value) {
            addCriterion("show_to_u2 <=", value, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2In(List<Boolean> values) {
            addCriterion("show_to_u2 in", values, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2NotIn(List<Boolean> values) {
            addCriterion("show_to_u2 not in", values, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2Between(Boolean value1, Boolean value2) {
            addCriterion("show_to_u2 between", value1, value2, "showToU2");
            return (Criteria) this;
        }

        public Criteria andShowToU2NotBetween(Boolean value1, Boolean value2) {
            addCriterion("show_to_u2 not between", value1, value2, "showToU2");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
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