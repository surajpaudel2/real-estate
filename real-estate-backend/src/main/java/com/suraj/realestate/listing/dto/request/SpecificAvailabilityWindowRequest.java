package com.suraj.realestate.listing.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * An availability window for a single specific date (e.g. "Dec 24, 10am-2pm").
 */
@Getter
@Setter
@NoArgsConstructor
public class SpecificAvailabilityWindowRequest extends AvailabilityWindowRequest {

    @Schema(example = "2026-12-24")
    @NotNull(message = "Date is required for a specific availability window.")
    private LocalDate date;
}
