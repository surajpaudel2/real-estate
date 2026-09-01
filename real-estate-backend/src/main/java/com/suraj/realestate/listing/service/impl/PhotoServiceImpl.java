package com.suraj.realestate.listing.service.impl;

import com.suraj.realestate.common.exception.ResourceNotFoundException;
import com.suraj.realestate.common.storage.service.StorageService;
import com.suraj.realestate.listing.dto.response.AddPhotosResponse;
import com.suraj.realestate.listing.dto.response.DeletePhotoResponse;
import com.suraj.realestate.listing.entity.Listing;
import com.suraj.realestate.listing.entity.Photo;
import com.suraj.realestate.listing.mapper.PhotoMapToMapper;
import com.suraj.realestate.listing.repository.ListingRepository;
import com.suraj.realestate.listing.service.PhotoService;
import com.suraj.realestate.listing.validator.ListingActiveValidator;
import com.suraj.realestate.listing.validator.ListingOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final ListingRepository listingRepository;
    private final StorageService storageService;
    private final ListingOwnershipValidator listingOwnershipValidator;
    private final ListingActiveValidator listingActiveValidator;
    private final PhotoMapToMapper photoMapToMapper;

    @Override
    @Transactional
    public AddPhotosResponse addPhotos(Long sellerId, Long listingId, List<MultipartFile> files) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        listingOwnershipValidator.validate(listing, sellerId);
        listingActiveValidator.validate(listing);

        List<String> urls = storageService.uploadAll(files);
        List<Photo> photos = urls.stream()
                .map(url -> Photo.builder()
                        .url(url)
                        .uploadDate(LocalDateTime.now())
                        .listing(listing)
                        .build())
                .toList();

        listing.getPhotos().addAll(photos);
        listingRepository.save(listing);

        return AddPhotosResponse.builder()
                .listingId(listing.getId())
                .photos(photos.stream().map(photoMapToMapper::mapFromPhoto).toList())
                .message("Photos added successfully.")
                .build();
    }

    @Override
    @Transactional
    public DeletePhotoResponse deletePhoto(Long sellerId, Long listingId, Long photoId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found."));

        listingOwnershipValidator.validate(listing, sellerId);
        listingActiveValidator.validate(listing);

        Photo photo = listing.getPhotos().stream()
                .filter(p -> p.getId().equals(photoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found."));

        // Removing from the managed collection (not deleting via a
        // repository directly) lets orphanRemoval on Listing.photos delete
        // the row, consistent with how availabilityWindows are replaced.
        listing.getPhotos().remove(photo);
        listingRepository.save(listing);

        storageService.delete(photo.getUrl());

        return DeletePhotoResponse.builder()
                .listingId(listingId)
                .photoId(photoId)
                .message("Photo deleted successfully.")
                .build();
    }
}
