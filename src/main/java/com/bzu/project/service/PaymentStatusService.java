package com.bzu.project.service;

import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.model.PaymentStatus;
import com.bzu.project.repository.PaymentStatusRepository;
import com.bzu.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public Page<PaymentStatus> getAllPaymentStatuses(PageRequest pageRequest) {
        return paymentStatusRepository.findAll(pageRequest);
    }

    public PaymentStatus getPaymentStatusById(Long id) {
        return paymentStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment status not found with id " + id));
    }

    public PaymentStatus createPaymentStatus(PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = convertToEntity(paymentStatusDTO);
        return paymentStatusRepository.save(paymentStatus);
    }

    public PaymentStatus updatePaymentStatus(Long id, PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment status not found with id " + id));
        paymentStatus.setPaymentStatusName(paymentStatusDTO.getPaymentStatusName());
        return paymentStatusRepository.save(paymentStatus);
    }

    public void deletePaymentStatus(Long id) {
        PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment status not found with id " + id));
        paymentStatusRepository.delete(paymentStatus);
    }

    private PaymentStatus convertToEntity(PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setId(paymentStatusDTO.getId());
        paymentStatus.setPaymentStatusName(paymentStatusDTO.getPaymentStatusName());
        return paymentStatus;
    }

    private PaymentStatusDTO convertToDTO(PaymentStatus paymentStatus) {
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setId(paymentStatus.getId());
        paymentStatusDTO.setPaymentStatusName(paymentStatus.getPaymentStatusName());
        return paymentStatusDTO;
    }
}
