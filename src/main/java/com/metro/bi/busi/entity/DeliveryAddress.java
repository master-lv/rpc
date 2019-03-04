package com.metro.bi.busi.entity;

import java.util.Date;

public class DeliveryAddress {

    public int getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(int deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCommunityAddressId() {
        return communityAddressId;
    }

    public void setCommunityAddressId(int communityAddressId) {
        this.communityAddressId = communityAddressId;
    }

    public String getRegionCommunityName() {
        return regionCommunityName;
    }

    public void setRegionCommunityName(String regionCommunityName) {
        this.regionCommunityName = regionCommunityName;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    private int deliveryAddressId;
    private int accountId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    private String accountName;
    private int communityAddressId;
    private String regionCommunityName;

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    private String fullAddress;
    private String consignee;
    private String detailedAddress;
    private String telephone;
    private String tel;
    private String email;
    private Date createTime;
    private int isDefault;


}
