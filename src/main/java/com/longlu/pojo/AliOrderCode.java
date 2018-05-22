package com.longlu.pojo;

public class AliOrderCode {
    private Long id;

    private String outerCode;

    private String alibabaUrl;

    private String goodsName;

    private String cost;

    private String supplierName;

    private String bz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOuterCode() {
        return outerCode;
    }

    public void setOuterCode(String outerCode) {
        this.outerCode = outerCode == null ? null : outerCode.trim();
    }

    public String getAlibabaUrl() {
        return alibabaUrl;
    }

    public void setAlibabaUrl(String alibabaUrl) {
        this.alibabaUrl = alibabaUrl == null ? null : alibabaUrl.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost == null ? null : cost.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}