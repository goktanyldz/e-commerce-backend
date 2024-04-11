package com.goktan.ecommercebackend.model;

import lombok.Data;

@Data
public class PaymentDetails {

    private String paymentMethod;

    private  String paymentStatus;

    private String paymentId;

    private String paymentLinkId;

    private String paymentLinkReferenceId;

    private String paymentLinkStatus;

    private String secondPaymentId;
}
