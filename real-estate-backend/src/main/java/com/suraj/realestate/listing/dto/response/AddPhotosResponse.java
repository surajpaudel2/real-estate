package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "Result of adding photos to a listing")
@Getter
@Builder
@AllArgsConstructor
public class AddPhotosResponse {

    @Schema(example = "1")
    private Long listingId;

    private List<PhotoResponse> photos;

    @Schema(example = "Photos added successfully.")
    private String message;
}
