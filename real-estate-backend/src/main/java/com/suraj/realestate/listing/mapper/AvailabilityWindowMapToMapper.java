package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.request.AvailabilityWindowRequest;
import com.suraj.realestate.listing.dto.request.RecurringAvailabilityWindowRequest;
import com.suraj.realestate.listing.dto.request.SpecificAvailabilityWindowRequest;
import com.suraj.realestate.listing.entity.AvailabilityWindow;
import com.suraj.realestate.listing.entity.RecurringAvailabilityWindow;
import com.suraj.realestate.listing.entity.SpecificAvailabilityWindow;
import org.springframework.stereotype.Component;

/**
 * Maps a polymorphic {@link AvailabilityWindowRequest} to the matching
 * {@link AvailabilityWindow} entity subtype. Adding a new window kind means
 * one new request subclass, one new entity subclass, and one new branch
 * here — no change to existing branches (OCP).
 */
@Component
public class AvailabilityWindowMapToMapper {

    public AvailabilityWindow mapFromRequest(AvailabilityWindowRequest request) {
        return switch (request) {
            case RecurringAvailabilityWindowRequest recurring -> RecurringAvailabilityWindow.builder()
                    .startTime(recurring.getStartTime())
                    .endTime(recurring.getEndTime())
                    .dayOfWeek(recurring.getDayOfWeek())
                    .build();
            case SpecificAvailabilityWindowRequest specific -> SpecificAvailabilityWindow.builder()
                    .startTime(specific.getStartTime())
                    .endTime(specific.getEndTime())
                    .date(specific.getDate())
                    .build();
            default -> throw new IllegalStateException(
                    "Unknown availability window request type: " + request.getClass().getSimpleName());
        };
    }
}
