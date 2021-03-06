package com.ycs.ezlink.mybatis.dto;

import java.util.ArrayList;
import java.util.List;

public class AlertTemplateMasterExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public AlertTemplateMasterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
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

        public Criteria andAlertTemplateIdIsNull() {
            addCriterion("ALERT_TEMPLATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdIsNotNull() {
            addCriterion("ALERT_TEMPLATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdEqualTo(String value) {
            addCriterion("ALERT_TEMPLATE_ID =", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdNotEqualTo(String value) {
            addCriterion("ALERT_TEMPLATE_ID <>", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdGreaterThan(String value) {
            addCriterion("ALERT_TEMPLATE_ID >", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("ALERT_TEMPLATE_ID >=", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdLessThan(String value) {
            addCriterion("ALERT_TEMPLATE_ID <", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("ALERT_TEMPLATE_ID <=", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdLike(String value) {
            addCriterion("ALERT_TEMPLATE_ID like", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdNotLike(String value) {
            addCriterion("ALERT_TEMPLATE_ID not like", value, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdIn(List<String> values) {
            addCriterion("ALERT_TEMPLATE_ID in", values, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdNotIn(List<String> values) {
            addCriterion("ALERT_TEMPLATE_ID not in", values, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdBetween(String value1, String value2) {
            addCriterion("ALERT_TEMPLATE_ID between", value1, value2, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTemplateIdNotBetween(String value1, String value2) {
            addCriterion("ALERT_TEMPLATE_ID not between", value1, value2, "alertTemplateId");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIsNull() {
            addCriterion("ALERT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIsNotNull() {
            addCriterion("ALERT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAlertTypeEqualTo(String value) {
            addCriterion("ALERT_TYPE =", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotEqualTo(String value) {
            addCriterion("ALERT_TYPE <>", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeGreaterThan(String value) {
            addCriterion("ALERT_TYPE >", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ALERT_TYPE >=", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeLessThan(String value) {
            addCriterion("ALERT_TYPE <", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeLessThanOrEqualTo(String value) {
            addCriterion("ALERT_TYPE <=", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeLike(String value) {
            addCriterion("ALERT_TYPE like", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotLike(String value) {
            addCriterion("ALERT_TYPE not like", value, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeIn(List<String> values) {
            addCriterion("ALERT_TYPE in", values, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotIn(List<String> values) {
            addCriterion("ALERT_TYPE not in", values, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeBetween(String value1, String value2) {
            addCriterion("ALERT_TYPE between", value1, value2, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertTypeNotBetween(String value1, String value2) {
            addCriterion("ALERT_TYPE not between", value1, value2, "alertType");
            return (Criteria) this;
        }

        public Criteria andAlertContentIsNull() {
            addCriterion("ALERT_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andAlertContentIsNotNull() {
            addCriterion("ALERT_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andAlertContentEqualTo(String value) {
            addCriterion("ALERT_CONTENT =", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentNotEqualTo(String value) {
            addCriterion("ALERT_CONTENT <>", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentGreaterThan(String value) {
            addCriterion("ALERT_CONTENT >", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentGreaterThanOrEqualTo(String value) {
            addCriterion("ALERT_CONTENT >=", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentLessThan(String value) {
            addCriterion("ALERT_CONTENT <", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentLessThanOrEqualTo(String value) {
            addCriterion("ALERT_CONTENT <=", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentLike(String value) {
            addCriterion("ALERT_CONTENT like", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentNotLike(String value) {
            addCriterion("ALERT_CONTENT not like", value, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentIn(List<String> values) {
            addCriterion("ALERT_CONTENT in", values, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentNotIn(List<String> values) {
            addCriterion("ALERT_CONTENT not in", values, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentBetween(String value1, String value2) {
            addCriterion("ALERT_CONTENT between", value1, value2, "alertContent");
            return (Criteria) this;
        }

        public Criteria andAlertContentNotBetween(String value1, String value2) {
            addCriterion("ALERT_CONTENT not between", value1, value2, "alertContent");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordIsNull() {
            addCriterion("SMS_PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordIsNotNull() {
            addCriterion("SMS_PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordEqualTo(String value) {
            addCriterion("SMS_PASSWORD =", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordNotEqualTo(String value) {
            addCriterion("SMS_PASSWORD <>", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordGreaterThan(String value) {
            addCriterion("SMS_PASSWORD >", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("SMS_PASSWORD >=", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordLessThan(String value) {
            addCriterion("SMS_PASSWORD <", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordLessThanOrEqualTo(String value) {
            addCriterion("SMS_PASSWORD <=", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordLike(String value) {
            addCriterion("SMS_PASSWORD like", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordNotLike(String value) {
            addCriterion("SMS_PASSWORD not like", value, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordIn(List<String> values) {
            addCriterion("SMS_PASSWORD in", values, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordNotIn(List<String> values) {
            addCriterion("SMS_PASSWORD not in", values, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordBetween(String value1, String value2) {
            addCriterion("SMS_PASSWORD between", value1, value2, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsPasswordNotBetween(String value1, String value2) {
            addCriterion("SMS_PASSWORD not between", value1, value2, "smsPassword");
            return (Criteria) this;
        }

        public Criteria andSmsUseridIsNull() {
            addCriterion("SMS_USERID is null");
            return (Criteria) this;
        }

        public Criteria andSmsUseridIsNotNull() {
            addCriterion("SMS_USERID is not null");
            return (Criteria) this;
        }

        public Criteria andSmsUseridEqualTo(String value) {
            addCriterion("SMS_USERID =", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridNotEqualTo(String value) {
            addCriterion("SMS_USERID <>", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridGreaterThan(String value) {
            addCriterion("SMS_USERID >", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridGreaterThanOrEqualTo(String value) {
            addCriterion("SMS_USERID >=", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridLessThan(String value) {
            addCriterion("SMS_USERID <", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridLessThanOrEqualTo(String value) {
            addCriterion("SMS_USERID <=", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridLike(String value) {
            addCriterion("SMS_USERID like", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridNotLike(String value) {
            addCriterion("SMS_USERID not like", value, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridIn(List<String> values) {
            addCriterion("SMS_USERID in", values, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridNotIn(List<String> values) {
            addCriterion("SMS_USERID not in", values, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridBetween(String value1, String value2) {
            addCriterion("SMS_USERID between", value1, value2, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andSmsUseridNotBetween(String value1, String value2) {
            addCriterion("SMS_USERID not between", value1, value2, "smsUserid");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdIsNull() {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID is null");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdIsNotNull() {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdEqualTo(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID =", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdNotEqualTo(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID <>", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdGreaterThan(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID >", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdGreaterThanOrEqualTo(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID >=", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdLessThan(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID <", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdLessThanOrEqualTo(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID <=", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdLike(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID like", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdNotLike(String value) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID not like", value, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdIn(List<String> values) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID in", values, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdNotIn(List<String> values) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID not in", values, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdBetween(String value1, String value2) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID between", value1, value2, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andTechStudioCampaignIdNotBetween(String value1, String value2) {
            addCriterion("TECH_STUDIO_CAMPAIGN_ID not between", value1, value2, "techStudioCampaignId");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIsNull() {
            addCriterion("EMAIL_SUBJECT is null");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIsNotNull() {
            addCriterion("EMAIL_SUBJECT is not null");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectEqualTo(String value) {
            addCriterion("EMAIL_SUBJECT =", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotEqualTo(String value) {
            addCriterion("EMAIL_SUBJECT <>", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectGreaterThan(String value) {
            addCriterion("EMAIL_SUBJECT >", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL_SUBJECT >=", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLessThan(String value) {
            addCriterion("EMAIL_SUBJECT <", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLessThanOrEqualTo(String value) {
            addCriterion("EMAIL_SUBJECT <=", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectLike(String value) {
            addCriterion("EMAIL_SUBJECT like", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotLike(String value) {
            addCriterion("EMAIL_SUBJECT not like", value, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectIn(List<String> values) {
            addCriterion("EMAIL_SUBJECT in", values, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotIn(List<String> values) {
            addCriterion("EMAIL_SUBJECT not in", values, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectBetween(String value1, String value2) {
            addCriterion("EMAIL_SUBJECT between", value1, value2, "emailSubject");
            return (Criteria) this;
        }

        public Criteria andEmailSubjectNotBetween(String value1, String value2) {
            addCriterion("EMAIL_SUBJECT not between", value1, value2, "emailSubject");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ALERT_TEMPLATE_MASTER
     *
     * @mbggenerated
     */
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