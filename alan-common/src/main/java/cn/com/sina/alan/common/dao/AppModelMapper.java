package cn.com.sina.alan.common.dao;

import cn.com.sina.alan.common.model.AppModel;
import org.springframework.stereotype.Repository;

@Repository
public interface AppModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer appId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int insert(AppModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int insertSelective(AppModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    AppModel selectByPrimaryKey(Integer appId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AppModel record);
}