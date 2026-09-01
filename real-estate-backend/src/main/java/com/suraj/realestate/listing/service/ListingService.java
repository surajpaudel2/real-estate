package com.suraj.realestate.listing.service;

import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.request.UpdateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.dto.response.ListingResponse;
import com.suraj.realestate.listing.dto.response.ListingStatusResponse;
import com.suraj.realestate.listing.dto.response.UpdateListingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {

    CreateListingResponse createListing(Long sellerId, CreateListingRequest request);

    UpdateListingResponse updateListing(Long sellerId, Long listingId, UpdateListingRequest request);

    ListingStatusResponse activateListing(Long sellerId, Long listingId);

    ListingStatusResponse deactivateListing(Long sellerId, Long listingId);

    ListingResponse getListing(Long listingId);

    Page<ListingResponse> getActiveListings(Pageable pageable);
}
