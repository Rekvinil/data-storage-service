package com.vkr.datastorageservice.data.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataServiceRequest {

    @NotNull
    @JsonProperty(required = true)
    private String id;

    @NotNull
    @JsonProperty(required = true)
    private RequestType type;

    @NotNull
    @JsonProperty(required = true)
    private String userId;

    @NotNull
    @JsonProperty(required = false)
    private String folderName;
}
