package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;

@Getter
@SuperBuilder
public class RecurringAvailabilityWindowResponse extends AvailabilityWindowResponse {

    @Schema(example = "MONDAY")
    private DayOfWeek dayOfWeek;
}
