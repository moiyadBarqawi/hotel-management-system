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

    @Override
    public EntityModel<PaymentStatusDTO> toModel(PaymentStatus entity) {
        PaymentStatusDTO paymentStatusDTO = convertToDTO(entity);

        return EntityModel.of(paymentStatusDTO,
                linkTo(methodOn(PaymentStatusController.class).getPaymentStatusById(paymentStatusDTO.getId())).withSelfRel(),
                linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses(0, 10)).withRel("payment-statuses"));
    }

    @Override
    public CollectionModel<EntityModel<PaymentStatusDTO>> toCollectionModel(Iterable<? extends PaymentStatus> entities) {
        List<EntityModel<PaymentStatusDTO>> paymentStatusDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(paymentStatusDTOs,
                linkTo(methodOn(PaymentStatusController.class).getAllPaymentStatuses(0, 10)).withSelfRel());
    }

    private PaymentStatusDTO convertToDTO(PaymentStatus paymentStatus) {
        PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO();
        paymentStatusDTO.setId(paymentStatus.getId());
        paymentStatusDTO.setPaymentStatusName(paymentStatus.getPaymentStatusName());
        return paymentStatusDTO;
    }
}
