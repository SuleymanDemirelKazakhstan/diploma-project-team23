package kz.sdu.project.sauapbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

    String uploadFile(MultipartFile file, String folder);

    void deleteFile(String fileName, String folder);

}
