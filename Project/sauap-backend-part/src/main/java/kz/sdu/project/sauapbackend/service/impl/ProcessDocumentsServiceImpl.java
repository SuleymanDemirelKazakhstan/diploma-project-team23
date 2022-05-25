package kz.sdu.project.sauapbackend.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import kz.sdu.project.sauapbackend.service.AmazonS3Service;
import kz.sdu.project.sauapbackend.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
@Qualifier("ProcessDocumentsServiceImpl")
public class ProcessDocumentsServiceImpl implements AmazonS3Service {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    @Override
    public String uploadFile(MultipartFile multipartFile, String folder) {
        File file = FileUtils.convertMultiPartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        String path = bucketName + folder;
        try {
            s3Client.putObject(path, fileName, file);
        } catch (AmazonServiceException ex) {
            log.error("Error while upload the file: {}/{}", path, fileName);
            throw new RuntimeException(String.format("Failed to upload the file: %s/%s", path, fileName), ex);
        }
        file.delete();
        return folder + "/" + fileName;
    }

    @Override
    public void deleteFile(String fileName, String folder) {
        String path = bucketName + folder;
        s3Client.deleteObject(path, fileName);
    }
}
