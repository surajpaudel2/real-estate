package com.suraj.realestate.listing.service;

import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.request.UpdateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.dto.response.UpdateListingResponse;

public interface ListingService {

    CreateListingResponse createListing(Long sellerId, CreateListingRequest request);

    UpdateListingResponse updateListing(Long sellerId, Long listingId, UpdateListingRequest request);
}
