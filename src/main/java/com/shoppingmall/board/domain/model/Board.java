package com.shoppingmall.board.domain.model;

import com.shoppingmall.board.enums.BoardTypeEnum;
import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tb_board")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) comment '게시글 타입'")
    private BoardTypeEnum type;

    @Column(columnDefinition = "bigint comment '작성자 시퀀스'")
    private Long userId;

    @Column(columnDefinition = "varchar(1000) comment '문의 내용 - 1,000자'")
    private String content;

}
