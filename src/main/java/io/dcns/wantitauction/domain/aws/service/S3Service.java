package io.dcns.wantitauction.domain.aws.service;

import com.amazonaws.SdkBaseException;
import com.amazonaws.services.s3.AmazonS3;
import io.dcns.wantitauction.domain.aws.repository.AmazonS3Repository;
import io.dcns.wantitauction.global.utils.MultiPartUtils;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Repository amazonS3Repository;

    public String uploadFile(MultipartFile file) {
        verifiedFile(file);
        String fullPath = MultiPartUtils.createPath(file);

        return amazonS3Repository.store(fullPath, file);
    }

    public void delete(String fileUrl) throws SdkBaseException {
        amazonS3Repository.delete(fileUrl);
    }

    public void verifiedFile(MultipartFile file) throws IllegalArgumentException {
        String contentType = file.getContentType();
        assert contentType != null;

        if (ObjectUtils.isEmpty(contentType) | (!contentType.contains("image/jpeg") && !contentType.contains("image/png"))) {
            throw new IllegalArgumentException("File is empty");
        }
    }
}
