package com.suraj.realestate.listing.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "A photo attached to a listing")
@Getter
@Builder
@AllArgsConstructor
public class PhotoResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "https://bucket.s3.amazonaws.com/listings/1/photo1.jpg")
    private String url;

    @Schema(example = "2026-08-24T10:15:30")
    private LocalDateTime uploadDate;
}
