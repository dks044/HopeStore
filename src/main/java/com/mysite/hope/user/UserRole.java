package com.mysite.hope.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}

//UserRole은 열거 자료형(enum)으로 작성했다. ADMIN은 "ROLE_ADMIN", USER는 "ROLE_USER" 라는 값을 가지도록  
//그리고 상수 자료형이므로 @Setter없이 @Getter만 사용가능