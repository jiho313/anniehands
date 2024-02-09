package com.jiho.anniehands.common.file;

import com.jiho.anniehands.common.dto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    UploadFileResponse upload(String path, MultipartFile file) throws IOException;
    default UploadFileResponse uploadByPath(String path, MultipartFile file) throws IOException {
        return switch (path) {
            case "product" -> upload("anniehands/images/products/", file);
            case "adopt" -> upload("images/adopt/", file);
            default -> throw new IllegalArgumentException();
        };
    }

}
