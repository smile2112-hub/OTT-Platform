package com.cdac.flowflix.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.flowflix.dto.PaymentDTO;
import com.cdac.flowflix.model.Payment;
import com.cdac.flowflix.model.PaymentStatus;
import com.cdac.flowflix.model.Subscription;
import com.cdac.flowflix.model.User;
import com.cdac.flowflix.repository.PaymentRepository;
import com.cdac.flowflix.repository.SubscriptionRepository;
import com.cdac.flowflix.service.PaymentService;
import com.cdac.flowflix.service.SubscriptionService;
import com.cdac.flowflix.service.UserService;
import org.springframework.beans.factory.annotation.Value;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Value("${payment.upi.id:}")
    private String upiId;

    @Value("${payment.upi.name:FlowFlix}")
    private String upiName;

    // ======================================
    // CREATE PAYMENT
    // ======================================

    @Override
    public PaymentDTO createPayment(Long subscriptionId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElse(null);

        if (subscription == null) {
            return null;
        }

        Payment payment = new Payment();

        payment.setUser(subscription.getUser());

        payment.setSubscription(subscription);

        payment.setAmount(
                subscriptionService.getSubscriptionPrice(
                        subscription.getSubscriptionPlan()));

        payment.setCurrency("INR");

        payment.setPaymentMethod("ONLINE");

        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setPaymentDate(LocalDateTime.now());

        payment.setTransactionId(
                "TXN-" + System.currentTimeMillis());

        paymentRepository.save(payment);

        PaymentDTO dto = new PaymentDTO(payment);

        // generate activation reference for this payment (dummy unique id)
        String activationRef = "ACT-" + System.currentTimeMillis();
        payment.setActivationReference(activationRef);

        // If UPI merchant id is configured, normalize it and generate UPI link and QR
        if (upiId != null && !upiId.trim().isEmpty()) {

            String effectiveUpi = upiId.trim();
            if (!effectiveUpi.contains("@")) {
                effectiveUpi = effectiveUpi + "@upi";
            }

            String txn = payment.getTransactionId();

            String upiLink = String.format(
                    "upi://pay?pa=%s&pn=%s&am=%.2f&tn=%s",
                    effectiveUpi,
                    upiName.replaceAll(" ", "+"),
                    payment.getAmount(),
                    txn
            );

            dto.setUpiLink(upiLink);

            try {
                QRCodeWriter qrWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrWriter.encode(upiLink, BarcodeFormat.QR_CODE, 300, 300);
                BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(qrImage, "PNG", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                dto.setUpiQrBase64(base64);
            } catch (Exception e) {
                // ignore QR generation errors
            }

        }

        // store activation reference and a user-facing activation message
        paymentRepository.save(payment);
        dto.setActivationReference(activationRef);
        dto.setActivationMessage("Your subscription will start in next few hours");

        return dto;

    }

    // ======================================
    // VERIFY PAYMENT
    // ======================================

    @Override
    @Transactional
    public String verifyPayment(Long paymentId) {

        Payment payment = findById(paymentId);

        if (payment == null) {
            return "Payment Not Found";
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        Subscription subscription = payment.getSubscription();

        if (subscription != null) {

            subscription.setActive(true);

            subscriptionRepository.save(subscription);

        }

        return "Payment Verified Successfully";

    }

    @Override
    @Transactional
    public String verifyAny(String any) {
        try {

            User user = userService.getCurrentUser();

            if (user == null) {
                return "User not authenticated";
            }

            // find most recent pending payment for this user's subscriptions
            List<Payment> payments = paymentRepository.findBySubscriptionUser(user);

            Payment target = null;

            for (Payment p : payments) {
                if (p.getPaymentStatus() == PaymentStatus.PENDING) {
                    if (p.getPaymentDate() == null) {
                        // skip if no date
                        continue;
                    }

                    if (target == null) {
                        target = p;
                    } else if (target.getPaymentDate() == null) {
                        target = p;
                    } else if (p.getPaymentDate().isAfter(target.getPaymentDate())) {
                        target = p;
                    }
                }
            }

            if (target == null) {
                return "No pending payment found to verify";
            }

            // mark success and activate subscription
            target.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(target);

            Subscription subscription = target.getSubscription();

            if (subscription != null) {
                subscription.setActive(true);
                subscriptionRepository.save(subscription);
            }

            return "Payment Verified Successfully (dummy)";

        } catch (Exception e) {
            log.error("verifyAny failed", e);
            return "Verification failed: internal error";
        }

    }

    // ======================================
    // FIND PAYMENT
    // ======================================

    @Override
    public Payment findById(Long id) {

        return paymentRepository
                .findById(id)
                .orElse(null);

    }

    // ======================================
    // MY PAYMENTS
    // ======================================

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getMyPayments() {

        User user = userService.getCurrentUser();

        if (user == null) {

            return List.of();

        }

        return paymentRepository
                .findBySubscriptionUser(user)
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());

    }

    // ======================================
    // ALL PAYMENTS
    // ======================================

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {

        return paymentRepository
                .findAll()
                .stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());

    }

    // ======================================
    // TOTAL REVENUE
    // ======================================

    @Override
    public Long getTotalRevenue() {

        long revenue = 0;

        List<Payment> payments =
                paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS);

        for (Payment payment : payments) {

            if (payment.getAmount() != null) {

                revenue += payment.getAmount().longValue();

            }

        }

        return revenue;

    }

    // ======================================
    // MONTHLY REVENUE
    // ======================================

    @Override
    public Long getMonthlyRevenue() {

        long revenue = 0;

        LocalDate today = LocalDate.now();

        List<Payment> payments =
                paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS);

        for (Payment payment : payments) {

            if (payment.getPaymentDate() != null) {

                LocalDate paymentDate =
                        payment.getPaymentDate().toLocalDate();

                if (paymentDate.getMonthValue() == today.getMonthValue()
                        &&
                        paymentDate.getYear() == today.getYear()) {

                    revenue += payment.getAmount().longValue();

                }

            }

        }

        return revenue;

    }

    // ======================================
    // TODAY REVENUE
    // ======================================

    @Override
    public Long getTodayRevenue() {

        long revenue = 0;

        LocalDate today = LocalDate.now();

        List<Payment> payments =
                paymentRepository.findByPaymentStatus(
                        PaymentStatus.SUCCESS);

        for (Payment payment : payments) {

            if (payment.getPaymentDate() != null
                    &&
                    payment.getPaymentDate()
                            .toLocalDate()
                            .equals(today)) {

                revenue += payment.getAmount().longValue();

            }

        }

        return revenue;

    }

}