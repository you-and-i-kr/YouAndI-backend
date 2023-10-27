package com.example.coupleapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "refreshTokens")
public class RefreshTokenEntity {

    @Id @GeneratedValue
    private Long id;

    private String refreshToken;

    private String email;

    public RefreshTokenEntity(String token, String email) {
        this.refreshToken = token;
        this.email = email;
    }

    public RefreshTokenEntity updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}
