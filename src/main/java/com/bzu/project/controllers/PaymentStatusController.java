package com.bzu.project.controllers;


import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.service.PaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-statuses")
public class PaymentStatusController {

    @Autowired
    private PaymentStatusService paymentStatusService;

    @GetMapping
    public List<PaymentStatusDTO> getAllPaymentStatuses() {
        return paymentStatusService.getAllPaymentStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusDTO> getPaymentStatusById(@PathVariable Long id) {
        PaymentStatusDTO paymentStatusDTO = paymentStatusService.getPaymentStatusById(id);
        return paymentStatusDTO != null ? ResponseEntity.ok(paymentStatusDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PaymentStatusDTO createPaymentStatus(@RequestBody PaymentStatusDTO paymentStatusDTO) {
        return paymentStatusService.createPaymentStatus(paymentStatusDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentStatusDTO> updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatusDTO updatedPaymentStatus = paymentStatusService.updatePaymentStatus(id, paymentStatusDTO);
        return updatedPaymentStatus != null ? ResponseEntity.ok(updatedPaymentStatus) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
