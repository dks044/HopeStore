package com.mysite.hope.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    
    //주소
    private String postcode; //우편번호
    private String address; //주소
    private String address_detail; //상세주소
    
    @Column(unique = true)
    private String phone_number; //폰번호
    
    @Enumerated(EnumType.STRING)
    private UserRole role; //권한
    
}
