package com.bzu.project.controllers;

import com.bzu.project.assembler.PaymentStatusAssembler;
import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/payment-statuses", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;
    private final PaymentStatusAssembler paymentStatusAssembler;

    @GetMapping
    public CollectionModel<EntityModel<PaymentStatusDTO>> getAllPaymentStatuses() {
        List<PaymentStatusDTO> paymentStatuses = paymentStatusService.getAllPaymentStatuses();
        return paymentStatusAssembler.toCollectionModel(paymentStatuses)
                .add(linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<PaymentStatusDTO> getPaymentStatusById(@PathVariable Long id) {
        PaymentStatusDTO paymentStatus = paymentStatusService.getPaymentStatusById(id);
        return paymentStatusAssembler.toModel(paymentStatus);
    }

    @PostMapping
    public ResponseEntity<EntityModel<PaymentStatusDTO>> createPaymentStatus(@RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatusDTO createdPaymentStatus = paymentStatusService.createPaymentStatus(paymentStatusDTO);
        URI location = linkTo(methodOn(PaymentStatusController.class).getPaymentStatusById(createdPaymentStatus.getId())).toUri();
        return ResponseEntity
                .created(location)
                .body(paymentStatusAssembler.toModel(createdPaymentStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PaymentStatusDTO>> updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatusDTO updatedPaymentStatus = paymentStatusService.updatePaymentStatus(id, paymentStatusDTO);
        return ResponseEntity.ok(paymentStatusAssembler.toModel(updatedPaymentStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
