package com.shoppingmall.file.domain.repository;

import com.shoppingmall.file.domain.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
