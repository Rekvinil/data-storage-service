package com.vkr.datastorageservice.data.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataServiceMessage {

    @NotNull
    @JsonProperty(required = true)
    private String id;

    @NotNull
    @JsonProperty(required = true)
    private RequestType type;

    @NotNull
    @JsonProperty(required = true)
    private String userId;

    @Valid
    @NotNull
    @JsonProperty(required = true)
    private Result result;

    public interface Result {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class ProjectListResult implements Result{

        @NotNull
        @JsonProperty(required = true)
        List<String> folderNames;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class FileListResult implements Result{

        @NotNull
        @JsonProperty
        String projectName;

        @NotNull
        @JsonProperty
        List<String> fileList;

        @NotNull
        @JsonProperty
        Map<String, String> fileLinks;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    public static class FailedResult implements Result{

        String message;
    }
}


