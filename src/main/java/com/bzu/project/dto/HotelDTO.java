package com.bzu.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class HotelDTO extends RepresentationModel<HotelDTO> {
    private Long id;
    private String name;
    private String location;
    private Integer rating;
}