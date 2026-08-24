package com.suraj.realestate.listing.dto.request;

import com.suraj.realestate.listing.enums.PropertyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Edit-listing payload. Carries every listing field except photos, which
 * are edited through the separate add/delete-photo endpoints.
 */
@Schema(description = "Edit-listing payload")
@Getter
@Setter
@NoArgsConstructor
public class UpdateListingRequest {

    @Schema(example = "APARTMENT")
    @NotNull(message = "Property type is required.")
    private PropertyType propertyType;

    @Schema(example = "1500.00")
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    private BigDecimal price;

    @Schema(example = "Sydney")
    @NotBlank(message = "City is required.")
    private String city;

    @Schema(example = "3")
    @NotNull(message = "Number of bedrooms is required.")
    @Min(value = 0, message = "Bedrooms cannot be negative.")
    private Integer bedrooms;

    @Schema(example = "2")
    @NotNull(message = "Number of bathrooms is required.")
    @Min(value = 0, message = "Bathrooms cannot be negative.")
    private Integer bathrooms;

    @Schema(example = "Bright, newly renovated apartment close to transit.")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters.")
    private String description;

    @Schema(example = "1")
    @NotNull(message = "Number of available parkings is required.")
    @Min(value = 0, message = "Available parkings cannot be negative.")
    private Integer availableParkings;

    @Schema(description = "At least one availability window is required")
    @NotEmpty(message = "At least one availability window is required.")
    @Valid
    private List<AvailabilityWindowRequest> availabilityWindows;
}
