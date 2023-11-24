package com.ssafy.homes.global.common.file;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDto {
    private Long id;
    private String originFileName;
    private String fullPath;
    private String saveFileName;
    private String realPath;

    public File toEntity() {
        return File.builder()
                .originFileName(this.originFileName)
                .fullPath(this.fullPath)
                .saveFileName(this.saveFileName)
                .realPath(this.realPath)
                .build();
    }

    @Builder
    private FileDto(Long id, String originFileName, String fullPath, String saveFileName, String realPath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fullPath = fullPath;
        this.saveFileName = saveFileName;
        this.realPath = realPath;
    }
}
