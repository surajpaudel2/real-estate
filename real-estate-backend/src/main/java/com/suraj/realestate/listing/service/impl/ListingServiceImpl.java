package com.suraj.realestate.listing.service.impl;

import com.suraj.realestate.common.exception.ResourceNotFoundException;
import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.mapper.CreateListingResponseMapToMapper;
import com.suraj.realestate.listing.mapper.ListingMapToMapper;
import com.suraj.realestate.listing.repository.ListingRepository;
import com.suraj.realestate.listing.service.ListingService;
import com.suraj.realestate.listing.validator.AvailabilityWindowValidator;
import com.suraj.realestate.user.entity.User;
import com.suraj.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final AvailabilityWindowValidator availabilityWindowValidator;
    private final ListingMapToMapper listingMapToMapper;
    private final CreateListingResponseMapToMapper createListingResponseMapToMapper;

    @Override
    @Transactional
    public CreateListingResponse createListing(Long sellerId, CreateListingRequest request) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found."));

        availabilityWindowValidator.validate(request.getAvailabilityWindows());

        Listing listing = listingMapToMapper.mapFromCreateRequest(request, seller);
        listing = listingRepository.save(listing);

        return createListingResponseMapToMapper.mapFromListing(listing);
    }
}
