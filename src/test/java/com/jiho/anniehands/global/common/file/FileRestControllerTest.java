package com.jiho.anniehands.global.common.file;

import com.jiho.anniehands.global.common.file.dto.UploadFileResponse;
import com.jiho.anniehands.web.controller.file.FileRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class FileRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @BeforeEach
    public void setup() {
        FileRestController fileRestController = new FileRestController(fileService);
        mockMvc = standaloneSetup(fileRestController).build();
    }

    private MultipartFile createMockMultipartFile() {
        String originFileName = "originFileName";
        String serverFileName = "serverFileName";
        String contentType = "text/plain";
        String content = "This is the file content";

        // MockMultipartFile 생성자는 파일명, 콘텐츠 타입, 실제 파일 컨텐츠를 파라미터로 받는다.
        return new MockMultipartFile(serverFileName, originFileName, contentType, content.getBytes());
    }
    @Test
    void testUploadFile() throws Exception {
        MultipartFile mockFile = createMockMultipartFile();
                UploadFileResponse mockResponse = new UploadFileResponse("originFileName", "serverFileName", 1024L);
        given(fileService.uploadByPath(anyString(), any(MultipartFile.class))).willReturn(mockResponse);

        mockMvc.perform(multipart("/upload/product")
                        .file("file", "file content".getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].originFileName").value("originFileName"))
                .andExpect(jsonPath("$[0].serverFileName").value("serverFileName"));
    }
}
