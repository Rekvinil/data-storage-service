package com.vkr.datastorageservice.service.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.vkr.datastorageservice.utils.TemporaryFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileStorageService {

    private final StorageAuthenticationService authenticationService;

    public FileStorageService(StorageAuthenticationService service) {
        this.authenticationService = service;
    }

    public boolean isFileExists(String relPath){
        AmazonS3 s3Client = authenticationService.getMinIOClient();
        return s3Client.doesObjectExist(authenticationService.getSourceBucket(), FilenameUtils.getName(relPath));
    }

    public void uploadFileToStorage(File file){
        sendToTheBucket(file);
    }

    public void uploadFilesToStorage(List<File> fileList){
        for(File file : fileList){
            sendToTheBucket(file);
        }
    }

    public void changeFile(String fileName, String content){
        Path tempFolder = null;
        File tmpFile = null;
        try {
            tempFolder = TemporaryFileUtils.newTemporaryFolder("FolderForFiles");
            tmpFile = tempFolder.resolve(fileName).toFile();
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(content.getBytes()), tmpFile);
            sendToTheBucket(tmpFile);
        } catch (Exception e){
            log.error("Can't change file");
        } finally {
            tmpFile.delete();
            tempFolder.toFile().delete();
        }

    }

    public String getHost(){
        return authenticationService.getHost();
    }

    public String getBucket(){
        return authenticationService.getSourceBucket();
    }

    public boolean removeFile(String sourceFile) {
        AmazonS3 s3Client = authenticationService.getMinIOClient();
        if (isFileExists(sourceFile)) {
            s3Client.deleteObject(authenticationService.getSourceBucket(), sourceFile);
        }
        return true;
    }

    private void sendToTheBucket(File file){
        AmazonS3 s3Client = authenticationService.getMinIOClient();
        PutObjectRequest putObjectRequest = new PutObjectRequest(authenticationService.getSourceBucket(), file.getName(), file);
        s3Client.putObject(putObjectRequest);
    }

    private int getFilesCount(String storagePath){
        AmazonS3 s3Client = authenticationService.getMinIOClient();
        ListObjectsV2Result result = s3Client.listObjectsV2(authenticationService.getSourceBucket(), storagePath);
        return result.getKeyCount();
    }

    public List<String> getListOfFiles(String folderName){
        AmazonS3 s3Client = authenticationService.getMinIOClient();
        ListObjectsV2Result result = s3Client.listObjectsV2(authenticationService.getSourceBucket(), folderName);
        List<S3ObjectSummary> objectSummaries = result.getObjectSummaries();
        return objectSummaries.stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }
}
