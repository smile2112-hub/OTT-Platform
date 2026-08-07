package com.cdac.flowflix.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // USER
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // ==========================================
    // SUBSCRIPTION
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    @JsonIgnore
    private Subscription subscription;

    // ==========================================
    // PAYMENT DETAILS
    // ==========================================

    private Double amount;

    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String paymentMethod;

    // ==========================================
    // RAZORPAY
    // ==========================================

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

    // ==========================================
    // STRIPE
    // ==========================================

    private String stripePaymentIntentId;

    // ==========================================
    // TRANSACTION
    // ==========================================

    @Column(unique = true)
    private String transactionId;

    private LocalDateTime paymentDate;

    private String remarks;
    
    // unique activation reference returned to the user after payment
    @Column(name = "activation_reference", unique = true)
    private String activationReference;

    // ==========================================
    // CONSTRUCTORS
    // ==========================================

    public Payment() {

    }

    public Payment(Long id,
                   User user,
                   Subscription subscription,
                   Double amount,
                   String currency,
                   PaymentStatus paymentStatus,
                   String paymentMethod,
                   String razorpayOrderId,
                   String razorpayPaymentId,
                   String razorpaySignature,
                   String stripePaymentIntentId,
                   String transactionId,
                   LocalDateTime paymentDate,
                   String remarks) {

        this.id = id;
        this.user = user;
        this.subscription = subscription;
        this.amount = amount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.stripePaymentIntentId = stripePaymentIntentId;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
        this.remarks = remarks;
    }

    // ==========================================
    // GETTERS & SETTERS
    // ==========================================

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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getActivationReference() {
        return activationReference;
    }

    public void setActivationReference(String activationReference) {
        this.activationReference = activationReference;
    }
}