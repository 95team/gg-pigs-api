package com.pangoapi.userSalt.entity;

import com.pangoapi.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class UserSalt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_salt_id")
    private Long id;

    @Column(length = 32)
    private String salt;

    @Column(length = 32)
    private String digest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    public static String generateDigest(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static UserSalt createUserSalt(String salt, String digest, User user) {
        return UserSalt.builder()
                .salt(salt)
                .digest(digest)
                .user(user)
                .build();
    }
}
