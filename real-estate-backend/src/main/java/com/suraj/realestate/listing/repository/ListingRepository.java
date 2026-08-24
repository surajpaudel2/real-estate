package com.suraj.realestate.listing.repository;

import com.suraj.realestate.listing.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
