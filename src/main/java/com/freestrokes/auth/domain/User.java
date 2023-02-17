package com.freestrokes.auth.domain;

import com.freestrokes.constants.AuthConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", length = 100, unique = true, nullable = false)
    private String userId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AuthConstants.Role role;

    public User updateUser(
        String name,
        String email,
        String picture,
        AuthConstants.Role role
    ) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;

        return this;
    }

    @Builder(toBuilder = true)
    public User(
        String userId,
        String name,
        String email,
        String picture,
        String password,
        AuthConstants.Role role
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.role = role;
    }

}
