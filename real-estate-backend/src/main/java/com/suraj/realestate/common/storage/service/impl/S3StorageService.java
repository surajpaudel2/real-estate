package com.suraj.realestate.common.storage.service.impl;

import com.suraj.realestate.common.storage.service.StorageService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * S3-backed {@link StorageService}. Wiring only for now — the actual AWS SDK
 * calls land with the S3 integration work; until then this exists purely so
 * {@code PhotoService} has a real bean to depend on.
 */
@Service
public class S3StorageService implements StorageService {

    @Override
    public List<String> uploadAll(List<MultipartFile> files) {
        throw new UnsupportedOperationException("S3 upload is not implemented yet.");
    }

    @Async
    @Override
    public void delete(String url) {
        throw new UnsupportedOperationException("S3 delete is not implemented yet.");
    }
}
