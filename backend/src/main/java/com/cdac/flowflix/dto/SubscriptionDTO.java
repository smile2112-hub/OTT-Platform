package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.SubscriptionPlan;

public class SubscriptionDTO {

    private String username;

    private SubscriptionPlan plan;

    public SubscriptionDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }

}