package com.vkr.datastorageservice.service;

import com.vkr.datastorageservice.data.db.User;
import com.vkr.datastorageservice.data.web.DataServiceMessage;
import com.vkr.datastorageservice.service.db.UserService;
import com.vkr.datastorageservice.service.file.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FolderMappingService {

    private final UserService userService;

    private final FileStorageService fileStorageService;

    public FolderMappingService(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    public List<String> getAllProjectsForUser(UUID id){
        User user = userService.getUserById(id);
        String folderName = user.getInitialFolder();
        return fileStorageService.getListOfFiles(folderName).stream().map(str -> str.split("/"))
                .filter(arr -> arr.length>1).map(arr->arr[1]).distinct().collect(Collectors.toList());
    }

    private List<String> getAllFilesOfProject(UUID id, String folderName){
        User user = userService.getUserById(id);
        String initialFolder = user.getInitialFolder();
        List<String> files = fileStorageService.getListOfFiles(initialFolder + "/" + folderName);
        if(files.isEmpty()){
            log.error("Folder is empty");
        }
        return files;
    }

    public DataServiceMessage.FileListResult getFilesList(UUID id, String folderName){
        DataServiceMessage.FileListResult fileListResult = new DataServiceMessage.FileListResult();

        List<String> files = getAllFilesOfProject(id, folderName);
        fileListResult.setFileList(files.stream().map(s -> {
            String[] sarr = s.split("/", 2);
            return sarr[1];
        }).collect(Collectors.toList()));
        Map<String, String> filesMap = files.stream().collect(Collectors.toMap(s -> {
            String[] sarr = s.split("/");
            return sarr[sarr.length-1];
        }, s -> fileStorageService.getHost()+ "/" + fileStorageService.getBucket() + "/" + s));

        fileListResult.setFileLinks(filesMap);
        fileListResult.setProjectName(folderName);

        return fileListResult;
    }
}
