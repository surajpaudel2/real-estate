package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Result of updating a listing")
@Getter
@Builder
@AllArgsConstructor
public class UpdateListingResponse {

    @Schema(example = "1")
    private Long listingId;

    @Schema(example = "Listing updated successfully.")
    private String message;
}
