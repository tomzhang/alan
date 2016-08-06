package cn.com.sina.alan.common.model;

import java.util.Date;

public class CustomerModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_id
     *
     * @mbggenerated
     */
    private Integer customerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.sso_id
     *
     * @mbggenerated
     */
    private String ssoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.sso_name
     *
     * @mbggenerated
     */
    private String ssoName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.client_name
     *
     * @mbggenerated
     */
    private String clientName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.agent_name
     *
     * @mbggenerated
     */
    private String agentName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.status
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.customer_type
     *
     * @mbggenerated
     */
    private Byte customerType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_id
     *
     * @return the value of customer.customer_id
     *
     * @mbggenerated
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_id
     *
     * @param customerId the value for customer.customer_id
     *
     * @mbggenerated
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.sso_id
     *
     * @return the value of customer.sso_id
     *
     * @mbggenerated
     */
    public String getSsoId() {
        return ssoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.sso_id
     *
     * @param ssoId the value for customer.sso_id
     *
     * @mbggenerated
     */
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId == null ? null : ssoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.sso_name
     *
     * @return the value of customer.sso_name
     *
     * @mbggenerated
     */
    public String getSsoName() {
        return ssoName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.sso_name
     *
     * @param ssoName the value for customer.sso_name
     *
     * @mbggenerated
     */
    public void setSsoName(String ssoName) {
        this.ssoName = ssoName == null ? null : ssoName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.client_name
     *
     * @return the value of customer.client_name
     *
     * @mbggenerated
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.client_name
     *
     * @param clientName the value for customer.client_name
     *
     * @mbggenerated
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.agent_name
     *
     * @return the value of customer.agent_name
     *
     * @mbggenerated
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.agent_name
     *
     * @param agentName the value for customer.agent_name
     *
     * @mbggenerated
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.create_time
     *
     * @return the value of customer.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.create_time
     *
     * @param createTime the value for customer.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.update_time
     *
     * @return the value of customer.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.update_time
     *
     * @param updateTime the value for customer.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.status
     *
     * @return the value of customer.status
     *
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.status
     *
     * @param status the value for customer.status
     *
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.customer_type
     *
     * @return the value of customer.customer_type
     *
     * @mbggenerated
     */
    public Byte getCustomerType() {
        return customerType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.customer_type
     *
     * @param customerType the value for customer.customer_type
     *
     * @mbggenerated
     */
    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
    }
}