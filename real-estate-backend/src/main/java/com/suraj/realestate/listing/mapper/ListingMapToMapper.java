package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.request.UpdateListingRequest;
import com.suraj.realestate.listing.entity.AvailabilityWindow;
import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.enums.ListingStatus;
import com.suraj.realestate.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListingMapToMapper {

    private final AvailabilityWindowMapToMapper availabilityWindowMapToMapper;

    public Listing mapFromCreateRequest(CreateListingRequest request, User seller) {
        Listing listing = Listing.builder()
                .propertyType(request.getPropertyType())
                .price(request.getPrice())
                .city(request.getCity())
                .bedrooms(request.getBedrooms())
                .bathrooms(request.getBathrooms())
                .description(request.getDescription())
                .availableParkings(request.getAvailableParkings())
                .status(ListingStatus.ACTIVE)
                .seller(seller)
                .build();

        List<AvailabilityWindow> windows = request.getAvailabilityWindows().stream()
                .map(availabilityWindowMapToMapper::mapFromRequest)
                .toList();

        // AvailabilityWindow.listing is the FK-owning side, so the cascade
        // from Listing.availabilityWindows won't populate it on its own.
        windows.forEach(window -> window.setListing(listing));
        listing.getAvailabilityWindows().addAll(windows);

        return listing;
    }

    public void updateFromRequest(Listing listing, UpdateListingRequest request) {
        listing.setPropertyType(request.getPropertyType());
        listing.setPrice(request.getPrice());
        listing.setCity(request.getCity());
        listing.setBedrooms(request.getBedrooms());
        listing.setBathrooms(request.getBathrooms());
        listing.setDescription(request.getDescription());
        listing.setAvailableParkings(request.getAvailableParkings());

        List<AvailabilityWindow> windows = request.getAvailabilityWindows().stream()
                .map(availabilityWindowMapToMapper::mapFromRequest)
                .toList();
        windows.forEach(window -> window.setListing(listing));

        // Mutate the existing managed collection in place rather than
        // replacing the reference — orphanRemoval only deletes rows dropped
        // from the same persistent collection instance Hibernate is tracking.
        listing.getAvailabilityWindows().clear();
        listing.getAvailabilityWindows().addAll(windows);
    }
}
