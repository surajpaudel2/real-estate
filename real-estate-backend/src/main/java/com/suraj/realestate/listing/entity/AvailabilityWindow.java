package com.suraj.realestate.listing.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A window of time in which a seller has declared a {@link Listing}
 * available for viewings. Implemented as single-table inheritance so both
 * recurring and one-off windows are fully substitutable for this base type
 * (LSP) — the booking validator calls {@link #covers} without ever needing
 * to know which subtype it is dealing with. Adding a new kind of window
 * (e.g. a blackout window) means one new subclass, not a change to callers.
 */
@Entity
@Table(name = "availability_windows")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "availability_type")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AvailabilityWindow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    private LocalTime startTime;

    private LocalTime endTime;

    /**
     * Whether this window covers the given requested date/time range.
     */
    public abstract boolean covers(LocalDate date, LocalTime requestedStart, LocalTime requestedEnd);
}
