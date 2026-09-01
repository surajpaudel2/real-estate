package com.suraj.realestate.listing.dto.response;

import com.suraj.realestate.listing.enums.ListingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Result of activating or deactivating a listing")
@Getter
@Builder
@AllArgsConstructor
public class ListingStatusResponse {

    @Schema(example = "1")
    private Long listingId;

    @Schema(example = "ACTIVE")
    private ListingStatus status;

    @Schema(example = "Listing activated successfully.")
    private String message;
}
