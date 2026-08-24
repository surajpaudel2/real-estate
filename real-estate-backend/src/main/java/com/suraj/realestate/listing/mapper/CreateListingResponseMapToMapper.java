package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class CreateListingResponseMapToMapper {

    public CreateListingResponse mapFromListing(Listing listing) {
        return CreateListingResponse.builder()
                .listingId(listing.getId())
                .message("Listing created successfully.")
                .build();
    }
}
