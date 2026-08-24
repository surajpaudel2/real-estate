package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.UpdateListingResponse;
import com.suraj.realestate.listing.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class UpdateListingResponseMapToMapper {

    public UpdateListingResponse mapFromListing(Listing listing) {
        return UpdateListingResponse.builder()
                .listingId(listing.getId())
                .message("Listing updated successfully.")
                .build();
    }
}
