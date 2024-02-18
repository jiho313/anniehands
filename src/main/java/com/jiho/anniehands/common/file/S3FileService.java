package com.jiho.anniehands.common.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.jiho.anniehands.common.dto.UploadFileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileService implements FileService{

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${s3.path.products}")
    private String s3ProductsPath;

    @Override
    public UploadFileResponse upload(String path, MultipartFile file) throws IOException {
        return uploadOnS3(path, file);
    }

    @Override
    public void deleteFile(String path, List<String> fileNames) {
        deleteFileOnS3(path, fileNames);
    }

    private UploadFileResponse uploadOnS3(String path, MultipartFile file) throws IOException{
        String originalFilename = file.getOriginalFilename();
        UploadFileResponse uploadFileResponse = null;
        try {
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();
            final String nanoId = NanoIdUtils.randomNanoId();
            final String key = path + nanoId;
            final PutObjectRequest request = new PutObjectRequest(bucketName, key, file.getInputStream(), getMetadataValue(file));

            log.info("오브젝트 요청 ====> {}", request);
            // TransferManager는 모든 전송을 비동기적으로 처리 따라서 이 호출은 즉시 반환
            final Upload upload = tm.upload(request);

            upload.waitForCompletion();
            log.info("Object 업로드 시작 -> {}({},{})", key, file.getContentType(), file.getSize());

            if ("Completed".equals(upload.getState().name())) {
                log.info("Object 업로드 성공 -> {}", originalFilename);
                uploadFileResponse = new UploadFileResponse(
                        originalFilename,
                        nanoId,
                        file.getSize());
            } else {
                log.info("Object 업로드 실패 -> {}", originalFilename);
            }
        } catch (AmazonServiceException e) {
            // Amazon S3의 서버 측 오류 처리
            log.error("Amazon S3 업로드 중 서버 측 오류 발생: {}, AWS 에러 코드: {}, HTTP 상태 코드: {}, AWS 오류 메시지: {}",
                    e.getMessage(), e.getErrorCode(), e.getStatusCode(), e.getErrorMessage());
        } catch (SdkClientException e) {
            // Amazon S3 클라이언트 측 오류 처리
            log.error("Amazon S3 업로드 중 클라이언트 측 오류 발생: {}", e.getMessage(), e);
        } catch (InterruptedException e) {
            // 인터럽트 발생 시, 현재 스레드의 인터럽트 상태를 설정
            Thread.currentThread().interrupt();
            log.error("업로드 중 인터럽트 발생: {}", e.getMessage(), e);
            throw new RuntimeException("업로드 중 인터럽트 발생", e);
        }
        return uploadFileResponse;
    }

    private void deleteFileOnS3(String path, List<String> fileNames) {
        for (String name : fileNames) {
            String key = path + name;
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
            log.info("버킷에서 이미지 삭제 완료: " + key);
        }
    }

    private ObjectMetadata getMetadataValue(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

}
