package com.shoppingmall.file.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tb_file")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class FileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(1000) comment '파일 경로'")
    private String filePath;

    @Column(columnDefinition = "varchar(255) comment '파일 오리지널 이름'")
    private String fileOriginalName;

    @Column(columnDefinition = "varchar(255) comment '파일 인코딩 이름'")
    private String fileEncName;

    @Column(columnDefinition = "varchar(255) comment '파일 타입'")
    private String fileType;

    @Column(columnDefinition = "varchar(255) comment '파일 사이즈'")
    private Long fileSize;

}
