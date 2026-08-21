package com.suraj.realestate.listing.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * An availability window that repeats weekly on a fixed day (e.g. "every
 * Monday 9am-5pm").
 */
@Entity
@DiscriminatorValue("RECURRING")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RecurringAvailabilityWindow extends AvailabilityWindow {

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Override
    public boolean covers(LocalDate date, LocalTime requestedStart, LocalTime requestedEnd) {
        return date.getDayOfWeek() == this.dayOfWeek
                && !requestedStart.isBefore(getStartTime())
                && !requestedEnd.isAfter(getEndTime());
    }
}
