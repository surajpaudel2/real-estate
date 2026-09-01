package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Result of deleting a listing photo")
@Getter
@Builder
@AllArgsConstructor
public class DeletePhotoResponse {

    @Schema(example = "1")
    private Long listingId;

    @Schema(example = "5")
    private Long photoId;

    @Schema(example = "Photo deleted successfully.")
    private String message;
}
