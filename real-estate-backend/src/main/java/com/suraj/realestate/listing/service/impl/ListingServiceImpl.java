package com.suraj.realestate.listing.service.impl;

import com.suraj.realestate.common.exception.ResourceNotFoundException;
import com.suraj.realestate.listing.dto.request.CreateListingRequest;
import com.suraj.realestate.listing.dto.request.UpdateListingRequest;
import com.suraj.realestate.listing.dto.response.CreateListingResponse;
import com.suraj.realestate.listing.dto.response.ListingResponse;
import com.suraj.realestate.listing.dto.response.ListingStatusResponse;
import com.suraj.realestate.listing.dto.response.UpdateListingResponse;
import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.enums.ListingStatus;
import com.suraj.realestate.listing.mapper.CreateListingResponseMapToMapper;
import com.suraj.realestate.listing.mapper.ListingMapToMapper;
import com.suraj.realestate.listing.mapper.ListingResponseMapToMapper;
import com.suraj.realestate.listing.mapper.ListingStatusResponseMapToMapper;
import com.suraj.realestate.listing.mapper.UpdateListingResponseMapToMapper;
import com.suraj.realestate.listing.repository.ListingRepository;
import com.suraj.realestate.listing.service.ListingService;
import com.suraj.realestate.listing.validator.AvailabilityWindowValidator;
import com.suraj.realestate.listing.validator.ListingOwnershipValidator;
import com.suraj.realestate.user.entity.User;
import com.suraj.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final AvailabilityWindowValidator availabilityWindowValidator;
    private final ListingOwnershipValidator listingOwnershipValidator;
    private final ListingMapToMapper listingMapToMapper;
    private final CreateListingResponseMapToMapper createListingResponseMapToMapper;
    private final UpdateListingResponseMapToMapper updateListingResponseMapToMapper;
    private final ListingStatusResponseMapToMapper listingStatusResponseMapToMapper;
    private final ListingResponseMapToMapper listingResponseMapToMapper;

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

    @Override
    @Transactional
    public UpdateListingResponse updateListing(Long sellerId, Long listingId, UpdateListingRequest request) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        listingOwnershipValidator.validate(listing, sellerId);
        availabilityWindowValidator.validate(request.getAvailabilityWindows());

        listingMapToMapper.updateFromRequest(listing, request);
        listing = listingRepository.save(listing);

        return updateListingResponseMapToMapper.mapFromListing(listing);
    }

    @Override
    @Transactional
    public ListingStatusResponse activateListing(Long sellerId, Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        listingOwnershipValidator.validate(listing, sellerId);

        listing.setStatus(ListingStatus.ACTIVE);
        listing = listingRepository.save(listing);

        return listingStatusResponseMapToMapper.mapFromListing(listing, "Listing activated successfully.");
    }

    @Override
    @Transactional
    public ListingStatusResponse deactivateListing(Long sellerId, Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        listingOwnershipValidator.validate(listing, sellerId);

        listing.setStatus(ListingStatus.DEACTIVE);
        listing = listingRepository.save(listing);

        return listingStatusResponseMapToMapper.mapFromListing(listing, "Listing deactivated successfully.");
    }

    @Override
    public ListingResponse getListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        return listingResponseMapToMapper.mapFromListing(listing);
    }

    @Override
    public Page<ListingResponse> getActiveListings(Pageable pageable) {
        Page<Listing> listings = listingRepository.findByStatus(ListingStatus.ACTIVE, pageable);
        return listings.map(listingResponseMapToMapper::mapFromListing);
    }
}
