package com.suraj.realestate.listing.controller;

import com.suraj.realestate.common.openapi.StandardApiResponses;
import com.suraj.realestate.common.response.ApiResponse;
import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.service.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Create a new listing", description = "Requires the SELLER role.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<CreateListingResponse>> createListing(
            @Valid @RequestBody CreateListingRequest request,
            @AuthenticationPrincipal Long sellerId) {
        CreateListingResponse response = listingService.createListing(sellerId, request);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }
}
