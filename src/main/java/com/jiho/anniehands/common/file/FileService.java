package com.jiho.anniehands.common.file;

import com.jiho.anniehands.common.dto.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    UploadFileResponse upload(String path, MultipartFile file) throws IOException;
    // path에 따른 분기점을 나누기 위한 디폴트 메서드 실제 업로드 로직은 오버라이드한 upload 메서드가 의존하는 uploadOnS3 메서드 존재
    default UploadFileResponse uploadByPath(String path, MultipartFile file) throws IOException {
        return switch (path) {
            case "product" -> upload("anniehands/images/products/", file);
            case "adopt" -> upload("anniehands/images/adopt/", file);
            default -> throw new IllegalArgumentException();
        };
    }

    void deleteFile(String path, List<String> fileNames);
    // path에 따른 분기점을 나누기 위한 디폴트 메서드 실제 삭제 로직은 오버라이드한 deleteFile 메서드가 의존하는 deleteFileOnS3 메서드에 존재
    default void deleteFileByPath(String path, List<String> fileNames) {
        switch (path) {
            case "product" -> deleteFile("anniehands/images/products/", fileNames);
            case "adopt" -> deleteFile("anniehands/images/adopt/", fileNames);
            default -> throw new IllegalArgumentException();
        }
    }

}
