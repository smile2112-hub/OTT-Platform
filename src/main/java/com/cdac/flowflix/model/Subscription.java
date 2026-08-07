package com.cdac.flowflix.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;

    private LocalDate startDate;

    private LocalDate expiryDate;

    private boolean active;

    public Subscription() {

    }

    public Subscription(Long id,
                        User user,
                        SubscriptionPlan subscriptionPlan,
                        LocalDate startDate,
                        LocalDate expiryDate,
                        boolean active) {

        this.id = id;
        this.user = user;
        this.subscriptionPlan = subscriptionPlan;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.active = active;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public User getUser() {

        return user;

    }

    public void setUser(User user) {

        this.user = user;

    }

    public SubscriptionPlan getSubscriptionPlan() {

        return subscriptionPlan;

    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {

        this.subscriptionPlan = subscriptionPlan;

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

}