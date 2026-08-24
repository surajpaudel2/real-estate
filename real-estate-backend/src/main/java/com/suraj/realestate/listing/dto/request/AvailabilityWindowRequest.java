package com.suraj.realestate.listing.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Request-side counterpart to {@link com.suraj.realestate.listing.entity.AvailabilityWindow}.
 * Mirrors the entity's single-table-inheritance shape instead of flattening
 * both subtypes into one DTO with nullable fields, so each subtype only
 * declares the data it actually needs — the {@code type} property drives
 * Jackson polymorphic deserialization into the matching subclass.
 */
@Schema(description = "An availability window; shape depends on 'type'",
        discriminatorProperty = "type",
        oneOf = {RecurringAvailabilityWindowRequest.class, SpecificAvailabilityWindowRequest.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RecurringAvailabilityWindowRequest.class, name = "RECURRING"),
        @JsonSubTypes.Type(value = SpecificAvailabilityWindowRequest.class, name = "SPECIFIC")
})
@Getter
@Setter
@NoArgsConstructor
public abstract class AvailabilityWindowRequest {

    @Schema(example = "09:00:00")
    @NotNull(message = "Availability window start time is required.")
    private LocalTime startTime;

    @Schema(example = "17:00:00")
    @NotNull(message = "Availability window end time is required.")
    private LocalTime endTime;
}
