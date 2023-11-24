package com.ssafy.homes.global.common.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileUtils {

    private static final String REAL_PATH = "static";
    private static final Path UPLOAD_DIR = Paths.get("backend", "src", "main", "resources", REAL_PATH);

    /**
     * 단일 파일 업로드
     */
    public FileDto uploadFile(final MultipartFile multipartFile, final String addRealPath) {
        // 파일명 UUID 처리
        String saveName = generateSaveFileName(multipartFile.getOriginalFilename());
        // 파일 저장을 위한 절대경로 처리
        Path absolutePath = UPLOAD_DIR.toAbsolutePath();
        String uploadPath = getUploadPath(absolutePath, addRealPath) + File.separator;

        String fullPath = uploadPath + saveName;
        File uploadFile = new File(fullPath);

        try {
            log.debug("■■■■ 실제 파일 저장 경로 ■■■■ : {}", fullPath);
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path realpath = Paths.get(REAL_PATH, addRealPath);

        return FileDto.builder()
                .originFileName(multipartFile.getOriginalFilename())
                .saveFileName(saveName)
                .fullPath(uploadPath)
                .realPath(realpath.toString())
                .build();
    }

    private String generateSaveFileName(String fileName) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(fileName);
        return uuid + "." + extension;
    }

    /**
     * 업로드 경로 가져오기
     * @param addPath: 추가 경로가 있는경우
     */
    private String getUploadPath(Path absolutePath, String addPath) {
        return makeDirectories(absolutePath + File.separator + addPath);
    }

    /**
     * 업로드 폴더 생성 (날짜별로 생성함)
     */
    private String makeDirectories(final String path){
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        return dir.getPath();
    }

}