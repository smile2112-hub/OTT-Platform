package com.cdac.flowflix.dto;

import java.time.LocalDate;

import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.SubscriptionPlan;

public class SubscriptionDTO {

    private Long id;

    private String username;

    private String plan;

    private Double price;

    private LocalDate startDate;

    private LocalDate expiryDate;

    private boolean active;

    private long remainingDays;

    public SubscriptionDTO() {

    }

    public SubscriptionDTO(Subscription subscription) {

        this.id = subscription.getId();

        if (subscription.getUser() != null) {

            this.username = subscription.getUser().getUsername();

        }

        if (subscription.getSubscriptionPlan() != null) {

            this.plan =
                    subscription
                            .getSubscriptionPlan()
                            .name();

            switch (subscription.getSubscriptionPlan()) {

            case BASIC:

                this.price = 199.0;
                break;

            case STANDARD:

                this.price = 399.0;
                break;

            case PREMIUM:

                this.price = 699.0;
                break;

            default:

                this.price = 0.0;

            }

        }

        this.startDate = subscription.getStartDate();

        this.expiryDate = subscription.getExpiryDate();

        this.active = subscription.isActive();

        if (subscription.getExpiryDate() != null) {

            long days =
                    LocalDate.now()
                             .until(subscription.getExpiryDate())
                             .getDays();

            this.remainingDays =
                    Math.max(days, 0);

        }

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getPlan() {

        return plan;

    }

    public void setPlan(String plan) {

        this.plan = plan;

    }

    public Double getPrice() {

        return price;

    }

    public void setPrice(Double price) {

        this.price = price;

    }

    public LocalDate getStartDate() {

        return startDate;

    }

    public void setStartDate(LocalDate startDate) {

        this.startDate = startDate;

    }

    public LocalDate getExpiryDate() {

        return expiryDate;

    }

    public void setExpiryDate(LocalDate expiryDate) {

        this.expiryDate = expiryDate;

    }

    public boolean isActive() {

        return active;

    }

    public void setActive(boolean active) {

        this.active = active;

    }

    public long getRemainingDays() {

        return remainingDays;

    }

    public void setRemainingDays(long remainingDays) {

        this.remainingDays = remainingDays;

    }

}