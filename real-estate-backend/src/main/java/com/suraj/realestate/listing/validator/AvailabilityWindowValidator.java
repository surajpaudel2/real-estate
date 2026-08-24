package com.suraj.realestate.listing.validator;

import com.suraj.realestate.listing.dto.request.AvailabilityWindowRequest;
import com.suraj.realestate.listing.exception.InvalidAvailabilityWindowException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Cross-field availability-window rules that bean validation on the request
 * DTO can't express on its own.
 */
@Component
public class AvailabilityWindowValidator {

    public void validate(List<AvailabilityWindowRequest> windows) {
        for (AvailabilityWindowRequest window : windows) {
            if (!window.getStartTime().isBefore(window.getEndTime())) {
                throw new InvalidAvailabilityWindowException(
                        "Availability window start time must be before its end time.");
            }
        }
    }
}
