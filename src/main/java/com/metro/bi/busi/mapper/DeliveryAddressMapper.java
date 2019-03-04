package com.metro.bi.busi.mapper;

import com.metro.bi.busi.entity.DeliveryAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * Title: DeliveryAddress PO类与SQL的映射关系
 * Description: 以面向对象的方式对PO类DeliveryAddress的操作映射成为对应的持久化操作
 * @author lvbq
 * @created 2018-10-29
 */
public interface DeliveryAddressMapper {

    /**
     * @description 向数据库插入一条记录
     * @author admin
     * @created 2018-11-20
     * @param deliveryAddress
     */
    @Insert("insert into delivery_address(account_id, community_address_id, detailed_address, consignee, telephone) values (#{accountId},#{communityAddressId},#{detailedAddress},#{consignee},#{telephone})")
    @Options(useGeneratedKeys = true, keyProperty="deliveryAddressId")
    int insertUseGeneratedKeys(DeliveryAddress deliveryAddress);
    /**
     * @description 根据指定的accountId查询配送地址
     * @author admin
     * @created 2018-11-15
     * @param accountId
     * @return
     */
    @Select(" select delivery_address_id,t1.account_id,t3.account_name,t1.community_address_id,t2.region_community_name,t2.full_address,t1.detailed_address,t1.consignee,t1.telephone,t1.is_default " +
            " from delivery_address t1 left join community_address t2 on t1.community_address_id=t2.community_address_id" +
            " left join account t3 on t1.account_id = t3.account_id where t1.account_id = #{accountId} ")
    @Results(
            {
                    @Result(id=true,column="delivery_address_id",property="deliveryAddressId"),
                    @Result(column="account_id",property="accountId"),
                    @Result(column="account_name",property="accountName"),
                    @Result(column="community_address_id",property="communityAddressId"),
                    @Result(column="region_community_name",property="regionCommunityName"),
                    @Result(column="full_address",property="fullAddress"),
                    @Result(column="consignee",property="consignee"),
                    @Result(column="telephone",property="telephone"),
                    @Result(column="detailed_address",property="detailedAddress"),
                    @Result(column="is_default",property="isDefault")
            }
    )
    List<DeliveryAddress> findListByAccount(@Param("accountId") int accountId);

}
