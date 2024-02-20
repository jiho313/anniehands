package com.jiho.anniehands.global.common.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {

    private String originFileName;
    private String serverFileName;
    private Long size;

    public UploadFileResponse(String originFileName, String serverFileName, Long size) {
        this.originFileName = originFileName;
        this.serverFileName = serverFileName;
        this.size = size;
    }
}
