package com.cdac.flowflix.dto;

import java.time.LocalDateTime;

import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.model.PaymentStatus;

public class PaymentDTO {

    private Long id;

    private Long userId;

    private String username;

    private Long subscriptionId;

    private String subscriptionPlan;

    private Double amount;

    private String currency;

    private PaymentStatus paymentStatus;

    private String paymentMethod;

    private String transactionId;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String stripePaymentIntentId;

    private LocalDateTime paymentDate;

    private String remarks;
    private String upiLink;
    private String upiQrBase64;
    private String activationReference;
    private String activationMessage;

    public PaymentDTO() {

    }

    public PaymentDTO(Payment payment) {

        this.id = payment.getId();

        if (payment.getUser() != null) {

            this.userId = payment.getUser().getId();

            this.username = payment.getUser().getUsername();

        }

        if (payment.getSubscription() != null) {

            this.subscriptionId = payment.getSubscription().getId();

            if (payment.getSubscription().getSubscriptionPlan() != null) {

                this.subscriptionPlan =
                        payment.getSubscription()
                               .getSubscriptionPlan()
                               .name();

            }

        }

        this.amount = payment.getAmount();

        this.currency = payment.getCurrency();

        this.paymentStatus = payment.getPaymentStatus();

        this.paymentMethod = payment.getPaymentMethod();

        this.transactionId = payment.getTransactionId();

        this.razorpayOrderId = payment.getRazorpayOrderId();

        this.razorpayPaymentId = payment.getRazorpayPaymentId();

        this.stripePaymentIntentId = payment.getStripePaymentIntentId();

        this.paymentDate = payment.getPaymentDate();

        this.remarks = payment.getRemarks();

        // activation reference (if set on payment)
        try {
            this.activationReference = payment.getActivationReference();
        } catch (Exception e) {
            // ignore if field not present
        }

        // UPI link and QR are generated on payment creation if available
        try {
            java.lang.reflect.Method m1 = payment.getClass().getMethod("getTransactionId");
            // no-op: placeholder in DTO; service will set values if generated
        } catch (Exception e) {
            // ignore
        }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
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

    public String getUpiLink() {
        return upiLink;
    }

    public void setUpiLink(String upiLink) {
        this.upiLink = upiLink;
    }

    public String getUpiQrBase64() {
        return upiQrBase64;
    }

    public String getActivationReference() {
        return activationReference;
    }

    public void setActivationReference(String activationReference) {
        this.activationReference = activationReference;
    }

    public String getActivationMessage() {
        return activationMessage;
    }

    public void setActivationMessage(String activationMessage) {
        this.activationMessage = activationMessage;
    }

    public void setUpiQrBase64(String upiQrBase64) {
        this.upiQrBase64 = upiQrBase64;
    }

}