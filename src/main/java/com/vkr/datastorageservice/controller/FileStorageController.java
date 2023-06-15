package com.vkr.datastorageservice.controller;

import com.vkr.datastorageservice.data.web.DataServiceMessage;
import com.vkr.datastorageservice.data.web.DataServiceRequest;
import com.vkr.datastorageservice.data.web.RequestType;
import com.vkr.datastorageservice.service.FolderMappingService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileStorageController {

    private final FolderMappingService folderMappingService;

    public FileStorageController(FolderMappingService folderMappingService) {
        this.folderMappingService = folderMappingService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getProjects")
    public DataServiceMessage getProjectList(@RequestBody DataServiceRequest dataServiceRequest){
        log.info("getProjects");
        DataServiceMessage dataServiceMessage = new DataServiceMessage();
        if(dataServiceRequest.getType().equals(RequestType.REQUEST_LIST_PROJECTS)) {
            UUID userId = UUID.fromString(dataServiceRequest.getUserId());

            List<String> folders = folderMappingService.getAllProjectsForUser(userId);

            dataServiceMessage.setId(UUID.randomUUID().toString());
            dataServiceMessage.setType(RequestType.REQUEST_LIST_PROJECTS);
            dataServiceMessage.setUserId(dataServiceMessage.getUserId());
            dataServiceMessage.setResult(DataServiceMessage.ProjectListResult.builder().folderNames(folders).build());

        } else {
            dataServiceMessage.setId(UUID.randomUUID().toString());
            dataServiceMessage.setType(RequestType.FAILED_REQUEST);
            dataServiceMessage.setUserId(dataServiceMessage.getUserId());
            dataServiceMessage.setResult(DataServiceMessage.FailedResult.builder().message("Invalid Request!").build());
            log.error("Invalid Request");
        }


        return dataServiceMessage;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getFiles")
    public DataServiceMessage getProjectFiles(@RequestBody DataServiceRequest dataServiceRequest){
        log.info("getFiles");
        DataServiceMessage dataServiceMessage = new DataServiceMessage();
        if(dataServiceRequest.getType().equals(RequestType.REQUEST_PROJECT_FILES)) {

            dataServiceMessage.setId(UUID.randomUUID().toString());
            dataServiceMessage.setType(RequestType.REQUEST_PROJECT_FILES);
            dataServiceMessage.setUserId(dataServiceMessage.getUserId());
            dataServiceMessage.setResult(folderMappingService.getFilesList(UUID.fromString(dataServiceRequest.getUserId()), dataServiceRequest.getFolderName()));
        } else {
            dataServiceMessage.setId(UUID.randomUUID().toString());
            dataServiceMessage.setType(RequestType.FAILED_REQUEST);
            dataServiceMessage.setUserId(dataServiceMessage.getUserId());
            dataServiceMessage.setResult(DataServiceMessage.FailedResult.builder().message("Invalid Request!").build());
            log.error("Invalid Request");
        }

        return dataServiceMessage;
    }
}
