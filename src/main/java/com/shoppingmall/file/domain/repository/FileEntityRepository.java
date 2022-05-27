package com.shoppingmall.file.domain.repository;

import com.shoppingmall.file.domain.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
}
