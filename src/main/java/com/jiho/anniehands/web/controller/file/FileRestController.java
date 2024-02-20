package com.jiho.anniehands.web.controller.file;

import com.jiho.anniehands.global.common.file.FileService;
import com.jiho.anniehands.global.common.file.dto.UploadFileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/upload/{path}")
    public ResponseEntity<List<UploadFileResponse>> uploadFile(@PathVariable String path, @RequestParam("file") MultipartFile[] files) throws IOException {

        // 상품 이미지들 처리
        List<UploadFileResponse> uploadFileResponses = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFileResponse imageResponse = fileService.uploadByPath(path, file);
            uploadFileResponses.add(imageResponse);
        }

        return ResponseEntity.ok().body(uploadFileResponses);
    }
}
