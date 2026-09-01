package com.suraj.realestate.listing.repository;

import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.enums.ListingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findByStatus(ListingStatus status, Pageable pageable);
}
