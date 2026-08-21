package com.suraj.realestate.listing.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * An availability window for a single specific date (e.g. "Dec 24,
 * 10am-2pm").
 */
@Entity
@DiscriminatorValue("SPECIFIC")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class SpecificAvailabilityWindow extends AvailabilityWindow {

    private LocalDate date;

    @Override
    public boolean covers(LocalDate date, LocalTime requestedStart, LocalTime requestedEnd) {
        return date.equals(this.date)
                && !requestedStart.isBefore(getStartTime())
                && !requestedEnd.isAfter(getEndTime());
    }
}
