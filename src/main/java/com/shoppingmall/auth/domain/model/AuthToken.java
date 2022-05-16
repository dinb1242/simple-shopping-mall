package com.shoppingmall.auth.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tb_auth_token")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class AuthToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment 'Access Token'")
    private String accessToken;

    @Column(columnDefinition = "bigint comment '유저 시퀀스'")
    private Long userId;

}
