package com.shoppingmall.user.domain.model;

import com.shoppingmall.auth.domain.model.Roles;
import com.shoppingmall.boot.domain.BaseEntity;
import com.shoppingmall.user.dto.request.UserInfoUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100) comment '회원 아이디'")
    private String username;

    @Column(columnDefinition = "varchar(255) comment '회원 비밀번호(암호화)'")
    private String password;

    @Column(columnDefinition = "varchar(50) comment '회원 실명'")
    private String name;

    @Column(columnDefinition = "varchar(20) comment '휴대폰 번호'")
    private String phone;

    @Column(columnDefinition = "varchar(100) comment '이메일'")
    private String email;

    @Column(columnDefinition = "varchar(100) comment '주소'")
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_user_roles",
            joinColumns = @JoinColumn(name = "user_seq"),
            inverseJoinColumns = @JoinColumn(name = "roles_name")
    )
    private List<Roles> roles;

    public void updatePassword(String updatedPassword) {
        this.password = updatedPassword;
    }

    /**
     * 유저의 정보를 변경한다.
     * @param requestDto
     */
    public void updateInfo(UserInfoUpdateRequestDto requestDto) {
        if(requestDto.getName() != null)
            this.name = requestDto.getName();
        if(requestDto.getAddress() != null)
            this.address = requestDto.getAddress();
        if (requestDto.getEmail() != null)
            this.email = requestDto.getEmail();
        if (requestDto.getPhone() != null)
            this.phone = requestDto.getPhone();
    }
}
