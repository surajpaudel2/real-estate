package com.suraj.realestate.listing.mapper;

import com.suraj.realestate.listing.dto.response.PhotoResponse;
import com.suraj.realestate.listing.entity.Photo;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapToMapper {

    public PhotoResponse mapFromPhoto(Photo photo) {
        return PhotoResponse.builder()
                .id(photo.getId())
                .url(photo.getUrl())
                .uploadDate(photo.getUploadDate())
                .build();
    }
}
