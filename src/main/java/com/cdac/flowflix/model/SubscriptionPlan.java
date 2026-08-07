package com.cdac.flowflix.model;

public enum SubscriptionPlan {

    FREE(0.0, 0),

    BASIC(199.0, 30),

    STANDARD(399.0, 30),

    PREMIUM(699.0, 30),

    VIP(999.0, 30);

    private final Double price;

    private final Integer validityDays;

    SubscriptionPlan(Double price, Integer validityDays) {

        this.price = price;

        this.validityDays = validityDays;

    }

    public Double getPrice() {

        return price;

    }

    public Integer getValidityDays() {

        return validityDays;

    }

}