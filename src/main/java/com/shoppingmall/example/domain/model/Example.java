package com.shoppingmall.example.domain.model;

import com.shoppingmall.boot.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_example")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Example extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

}
