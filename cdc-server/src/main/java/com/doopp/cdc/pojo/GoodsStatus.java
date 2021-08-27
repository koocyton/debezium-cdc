package com.doopp.youlin.pojo;

public enum GoodsStatus {

    New("new"), OnSale("on-sale"), SoldOver("sold-over"), StopSelling("stop-selling");

    private String status;

    private GoodsStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
