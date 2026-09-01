package com.suraj.realestate.listing.dto.response;

import com.suraj.realestate.listing.enums.ListingStatus;
import com.suraj.realestate.listing.enums.PropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "A listing, for viewing")
@Getter
@Builder
@AllArgsConstructor
public class ListingResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "APARTMENT")
    private PropertyType propertyType;

    @Schema(example = "1500.00")
    private BigDecimal price;

    @Schema(example = "Sydney")
    private String city;

    @Schema(example = "3")
    private Integer bedrooms;

    @Schema(example = "2")
    private Integer bathrooms;

    @Schema(example = "Bright, newly renovated apartment close to transit.")
    private String description;

    @Schema(example = "1")
    private Integer availableParkings;

    @Schema(example = "ACTIVE")
    private ListingStatus status;

    @Schema(example = "7")
    private Long sellerId;

    @Schema(example = "Jane Seller")
    private String sellerName;

    private List<PhotoResponse> photos;

    private List<AvailabilityWindowResponse> availabilityWindows;
}
