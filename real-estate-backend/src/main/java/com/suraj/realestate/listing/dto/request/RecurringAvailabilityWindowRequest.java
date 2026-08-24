package com.suraj.realestate.listing.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

/**
 * An availability window that repeats weekly on a fixed day
 * (e.g. "every Monday 9am-5pm").
 */
@Getter
@Setter
@NoArgsConstructor
public class RecurringAvailabilityWindowRequest extends AvailabilityWindowRequest {

    @Schema(example = "MONDAY")
    @NotNull(message = "Day of week is required for a recurring availability window.")
    private DayOfWeek dayOfWeek;
}
