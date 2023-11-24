package com.ssafy.homes.global.common.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FileRepository extends JpaRepository<File, Long> {

    File findByFullPath(@Param("fullPath") String fullPath);
}
