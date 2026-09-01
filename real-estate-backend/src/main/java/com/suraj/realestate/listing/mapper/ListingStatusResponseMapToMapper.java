package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.ListingStatusResponse;
import com.suraj.realestate.listing.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class ListingStatusResponseMapToMapper {

    public ListingStatusResponse mapFromListing(Listing listing, String message) {
        return ListingStatusResponse.builder()
                .listingId(listing.getId())
                .status(listing.getStatus())
                .message(message)
                .build();
    }
}
