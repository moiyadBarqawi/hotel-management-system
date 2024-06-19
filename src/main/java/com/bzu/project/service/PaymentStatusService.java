package com.bzu.project.service;


import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.model.PaymentStatus;
import com.bzu.project.repository.PaymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentStatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public List<PaymentStatusDTO> getAllPaymentStatuses() {
        return paymentStatusRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PaymentStatusDTO getPaymentStatusById(Long id) {
        Optional<PaymentStatus> paymentStatus = paymentStatusRepository.findById(id);
        return paymentStatus.map(this::convertToDTO).orElse(null);
    }

    public PaymentStatusDTO createPaymentStatus(PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = convertToEntity(paymentStatusDTO);
        return convertToDTO(paymentStatusRepository.save(paymentStatus));
    }

    public PaymentStatusDTO updatePaymentStatus(Long id, PaymentStatusDTO paymentStatusDTO) {
        Optional<PaymentStatus> existingPaymentStatus = paymentStatusRepository.findById(id);
        if (existingPaymentStatus.isPresent()) {
            PaymentStatus paymentStatus = existingPaymentStatus.get();
            paymentStatus.setPaymentStatusName(paymentStatusDTO.getPaymentStatusName());
            return convertToDTO(paymentStatusRepository.save(paymentStatus));
        } else {
            return null;
        }
    }

    public void deletePaymentStatus(Long id) {
        paymentStatusRepository.deleteById(id);
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