package com.mysite.hope.user;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupForm {
	@Size(min = 3, max = 25)
	@NotEmpty(message = "사용자ID는 필수 항목 입니다.")
	private String username;
	
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2; 
	
    @NotEmpty(message = "주소는 필수항목 입니다.")
    private String address;
    
    @Size(max = 40)
    private String address_detail;
    
    @NotEmpty(message = "우편번호는 필수항목 입니다.")
    private String postcode;
    
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
    
    @NotEmpty(message = "전화번호는 필수항목입니다.")
    private String phone_number;
}
