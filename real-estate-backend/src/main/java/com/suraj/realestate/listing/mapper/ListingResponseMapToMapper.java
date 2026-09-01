package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.ListingResponse;
import com.suraj.realestate.listing.entity.Listing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListingResponseMapToMapper {

    private final PhotoMapToMapper photoMapToMapper;
    private final AvailabilityWindowResponseMapToMapper availabilityWindowResponseMapToMapper;

    public ListingResponse mapFromListing(Listing listing) {
        return ListingResponse.builder()
                .id(listing.getId())
                .propertyType(listing.getPropertyType())
                .price(listing.getPrice())
                .city(listing.getCity())
                .bedrooms(listing.getBedrooms())
                .bathrooms(listing.getBathrooms())
                .description(listing.getDescription())
                .availableParkings(listing.getAvailableParkings())
                .status(listing.getStatus())
                .sellerId(listing.getSeller().getId())
                .sellerName(listing.getSeller().getName())
                .photos(listing.getPhotos().stream().map(photoMapToMapper::mapFromPhoto).toList())
                .availabilityWindows(listing.getAvailabilityWindows().stream()
                        .map(availabilityWindowResponseMapToMapper::mapFromEntity)
                        .toList())
                .build();
    }
}
