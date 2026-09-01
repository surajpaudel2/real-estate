package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
public class SpecificAvailabilityWindowResponse extends AvailabilityWindowResponse {

    @Schema(example = "2026-12-24")
    private LocalDate date;
}
