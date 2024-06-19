package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class PaymentStatusDTO extends RepresentationModel<PaymentStatusDTO> {
    private Long id;
    private String paymentStatusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }
}