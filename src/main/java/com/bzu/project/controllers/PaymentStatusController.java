package com.bzu.project.controllers;

import com.bzu.project.assembler.PaymentStatusAssembler;
import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.model.PaymentStatus;
import com.bzu.project.service.PaymentStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/payment-statuses", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;
    private final PaymentStatusAssembler paymentStatusAssembler;

    @Operation(summary = "Get all payment statuses", description = "Retrieve a paginated list of all payment statuses")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<PaymentStatusDTO>> getAllPaymentStatuses(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PaymentStatus> paymentStatuses = paymentStatusService.getAllPaymentStatuses(pageRequest);
        return paymentStatusAssembler.toCollectionModel(paymentStatuses)
                .add(linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses(page, size)).withSelfRel());
    }

    @Operation(summary = "Get payment status by ID", description = "Retrieve a payment status by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payment status"),
            @ApiResponse(responseCode = "404", description = "Payment status not found")
    })
    @GetMapping("/{id}")
    public EntityModel<PaymentStatusDTO> getPaymentStatusById(@PathVariable Long id) {
        PaymentStatus paymentStatus = paymentStatusService.getPaymentStatusById(id);
        return paymentStatusAssembler.toModel(paymentStatus);
    }

    @Operation(summary = "Create a new payment status", description = "Add a new payment status to the system")
    @ApiResponse(responseCode = "201", description = "Payment status created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<PaymentStatusDTO>> createPaymentStatus(@RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = paymentStatusService.createPaymentStatus(paymentStatusDTO);
        URI location = linkTo(methodOn(PaymentStatusController.class).getPaymentStatusById(paymentStatus.getId())).toUri();
        return ResponseEntity.created(location).body(paymentStatusAssembler.toModel(paymentStatus));
    }

    @Operation(summary = "Update a payment status", description = "Update details of an existing payment status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment status not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PaymentStatusDTO>> updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus updatedPaymentStatus = paymentStatusService.updatePaymentStatus(id, paymentStatusDTO);
        return ResponseEntity.ok(paymentStatusAssembler.toModel(updatedPaymentStatus));
    }

    @Operation(summary = "Delete a payment status", description = "Delete a payment status from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment status deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment status not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
