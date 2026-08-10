/**
 * Author:  Mahesh Ganesh Kajale
 * Email:   maheshkajale2112@gmail.com
 * Project: FlowFlix OTT Platform
 */
package com.cdac.flowflix.model;

public enum SubscriptionPlan {

    FREE(0.0, 0),

    BASIC(199.0, 30),

    STANDARD(399.0, 30),

    PREMIUM(699.0, 30),

    VIP(999.0, 30);

    private final Double price;

    private final Integer validityDays;

// © FlowFlix OTT Platform | Author: Mahesh Ganesh Kajale <maheshkajale2112@gmail.com> | Unauthorized use prohibited.


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
