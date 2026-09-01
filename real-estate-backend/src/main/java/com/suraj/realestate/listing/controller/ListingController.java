package com.suraj.realestate.listing.controller;

import com.suraj.realestate.common.openapi.StandardApiResponses;
import com.suraj.realestate.common.response.ApiResponse;
import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.request.UpdateListingRequest;
import com.suraj.realestate.listing.dto.response.AddPhotosResponse;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.dto.response.DeletePhotoResponse;
import com.suraj.realestate.listing.dto.response.ListingResponse;
import com.suraj.realestate.listing.dto.response.ListingStatusResponse;
import com.suraj.realestate.listing.dto.response.UpdateListingResponse;
import com.suraj.realestate.listing.service.ListingService;
import com.suraj.realestate.listing.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;
    private final PhotoService photoService;

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

    @PutMapping("/{listingId}")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Edit an existing listing", description = "Requires the SELLER role and ownership of the listing.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<UpdateListingResponse>> updateListing(
            @PathVariable Long listingId,
            @Valid @RequestBody UpdateListingRequest request,
            @AuthenticationPrincipal Long sellerId) {
        UpdateListingResponse response = listingService.updateListing(sellerId, listingId, request);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @PatchMapping("/{listingId}/activate")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Activate a listing", description = "Requires the SELLER role and ownership of the listing.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<ListingStatusResponse>> activateListing(
            @PathVariable Long listingId,
            @AuthenticationPrincipal Long sellerId) {
        ListingStatusResponse response = listingService.activateListing(sellerId, listingId);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @PatchMapping("/{listingId}/deactivate")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Deactivate a listing", description = "Requires the SELLER role and ownership of the listing.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<ListingStatusResponse>> deactivateListing(
            @PathVariable Long listingId,
            @AuthenticationPrincipal Long sellerId) {
        ListingStatusResponse response = listingService.deactivateListing(sellerId, listingId);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @PostMapping(value = "/{listingId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Add photos to a listing", description = "Requires the SELLER role, ownership of the listing, and an ACTIVE listing.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<AddPhotosResponse>> addPhotos(
            @PathVariable Long listingId,
            @RequestPart("files") List<MultipartFile> files,
            @AuthenticationPrincipal Long sellerId) {
        AddPhotosResponse response = photoService.addPhotos(sellerId, listingId, files);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @DeleteMapping("/{listingId}/photos/{photoId}")
    @PreAuthorize("hasRole('SELLER')")
    @Operation(summary = "Delete a listing photo", description = "Requires the SELLER role, ownership of the listing, and an ACTIVE listing.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<DeletePhotoResponse>> deletePhoto(
            @PathVariable Long listingId,
            @PathVariable Long photoId,
            @AuthenticationPrincipal Long sellerId) {
        DeletePhotoResponse response = photoService.deletePhoto(sellerId, listingId, photoId);
        return ResponseEntity.ok(ApiResponse.success(response, response.getMessage()));
    }

    @GetMapping("/{listingId}")
    @Operation(summary = "View a listing", description = "Public endpoint.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<ListingResponse>> getListing(@PathVariable Long listingId) {
        ListingResponse response = listingService.getListing(listingId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @Operation(summary = "Browse active listings", description = "Public, paginated endpoint.")
    @StandardApiResponses
    public ResponseEntity<ApiResponse<Page<ListingResponse>>> getListings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ListingResponse> response = listingService.getActiveListings(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
