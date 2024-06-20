package com.bzu.project.assembler;

import com.bzu.project.controllers.PaymentStatusController;
import com.bzu.project.dto.PaymentStatusDTO;
import com.bzu.project.model.PaymentStatus;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PaymentStatusAssembler implements RepresentationModelAssembler<PaymentStatus, EntityModel<PaymentStatusDTO>> {

    public EntityModel<PaymentStatusDTO> toModel(PaymentStatusDTO paymentStatus) {
        PaymentStatusDTO paymentStatusDTO = convertToDTO(paymentStatus);

        return EntityModel.of(paymentStatusDTO,
                linkTo(methodOn(PaymentStatusController.class).getPaymentStatusById(paymentStatusDTO.getId())).withSelfRel(),
                linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses()).withRel("payment-statuses"));
    }

    public CollectionModel<EntityModel<PaymentStatusDTO>> toCollectionModel(List<PaymentStatusDTO> entities) {
        List<EntityModel<PaymentStatusDTO>> paymentStatusDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paymentStatusDTOs,
                linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses()).withSelfRel());
    }

    private PaymentStatusDTO convertToDTO(PaymentStatusDTO paymentStatus) {
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setId(paymentStatus.getId());
        paymentStatusDTO.setPaymentStatusName(paymentStatus.getPaymentStatusName());
        return paymentStatusDTO;
    }

    @Override
    public EntityModel<PaymentStatusDTO> toModel(PaymentStatus entity) {
        return null;
    }

    @Override
    public CollectionModel<EntityModel<PaymentStatusDTO>> toCollectionModel(Iterable<? extends PaymentStatus> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
