package com.suraj.realestate.common.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Extension point for wherever uploaded media actually lands (S3 today,
 * potentially something else later). Callers depend on this interface only,
 * never on a concrete implementation, so swapping providers never touches
 * client code.
 */
public interface StorageService {

    /**
     * Uploads every file and returns their resulting URLs, in the same order.
     */
    List<String> uploadAll(List<MultipartFile> files);

    /**
     * Deletes a single previously uploaded file by its URL.
     */
    void delete(String url);
}
