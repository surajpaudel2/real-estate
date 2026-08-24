package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.request.CreateListingRequest;
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
}
