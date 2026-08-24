package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Result of creating a listing")
@Getter
@Builder
@AllArgsConstructor
public class CreateListingResponse {

    @Schema(example = "1")
    private Long listingId;

    @Schema(example = "Listing created successfully.")
    private String message;
}
