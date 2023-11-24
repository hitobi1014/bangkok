package com.ssafy.homes.global.common.file;

import com.ssafy.homes.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class File extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String originFileName;
    @Column(nullable = false)
    private String saveFileName;
    @Column(nullable = false)
    private String fullPath;
    @Column(nullable = false)
    private String realPath;

    @Builder
    private File(String originFileName, String fullPath, String saveFileName, String realPath) {
        this.originFileName = originFileName;
        this.fullPath = fullPath;
        this.saveFileName = saveFileName;
        this.realPath = realPath;
    }


}
