package com.suraj.realestate.listing.service;

import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;

public interface ListingService {

    CreateListingResponse createListing(Long sellerId, CreateListingRequest request);
}
