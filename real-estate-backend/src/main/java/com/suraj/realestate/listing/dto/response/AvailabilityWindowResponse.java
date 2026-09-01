package com.suraj.realestate.listing.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

/**
 * Response-side counterpart to {@link com.suraj.realestate.listing.entity.AvailabilityWindow},
 * mirroring the request-side {@link AvailabilityWindowRequest}'s polymorphic
 * shape so each subtype only serializes the data it actually has.
 */
@Schema(description = "An availability window; shape depends on 'type'",
        discriminatorProperty = "type",
        oneOf = {RecurringAvailabilityWindowResponse.class, SpecificAvailabilityWindowResponse.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RecurringAvailabilityWindowResponse.class, name = "RECURRING"),
        @JsonSubTypes.Type(value = SpecificAvailabilityWindowResponse.class, name = "SPECIFIC")
})
@Getter
@SuperBuilder
public abstract class AvailabilityWindowResponse {

    @Schema(example = "09:00:00")
    private LocalTime startTime;

    @Schema(example = "17:00:00")
    private LocalTime endTime;
}
