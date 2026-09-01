package com.suraj.realestate.listing.service;

import com.suraj.realestate.listing.dto.response.AddPhotosResponse;
import com.suraj.realestate.listing.dto.response.DeletePhotoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    AddPhotosResponse addPhotos(Long sellerId, Long listingId, List<MultipartFile> files);

    DeletePhotoResponse deletePhoto(Long sellerId, Long listingId, Long photoId);
}
