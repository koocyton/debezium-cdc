package com.doopp.youlin.pojo;

public enum ReviewStatus {

    Review("review"), Accept("accept"), Reject("reject");

    private String status;

    private ReviewStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
