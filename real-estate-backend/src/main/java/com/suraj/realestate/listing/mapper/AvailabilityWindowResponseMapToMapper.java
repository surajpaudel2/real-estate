package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.AvailabilityWindowResponse;
import com.suraj.realestate.listing.dto.response.RecurringAvailabilityWindowResponse;
import com.suraj.realestate.listing.dto.response.SpecificAvailabilityWindowResponse;
import com.suraj.realestate.listing.entity.AvailabilityWindow;
import com.suraj.realestate.listing.entity.RecurringAvailabilityWindow;
import com.suraj.realestate.listing.entity.SpecificAvailabilityWindow;
import org.springframework.stereotype.Component;

/**
 * Maps a persisted {@link AvailabilityWindow} subtype to its matching
 * response DTO. Mirrors {@link AvailabilityWindowMapToMapper} (the
 * request-to-entity direction): adding a new window kind means one new
 * branch here, no change to existing ones (OCP).
 */
@Component
public class AvailabilityWindowResponseMapToMapper {

    public AvailabilityWindowResponse mapFromEntity(AvailabilityWindow window) {
        return switch (window) {
            case RecurringAvailabilityWindow recurring -> RecurringAvailabilityWindowResponse.builder()
                    .startTime(recurring.getStartTime())
                    .endTime(recurring.getEndTime())
                    .dayOfWeek(recurring.getDayOfWeek())
                    .build();
            case SpecificAvailabilityWindow specific -> SpecificAvailabilityWindowResponse.builder()
                    .startTime(specific.getStartTime())
                    .endTime(specific.getEndTime())
                    .date(specific.getDate())
                    .build();
            default -> throw new IllegalStateException(
                    "Unknown availability window entity type: " + window.getClass().getSimpleName());
        };
    }
}
