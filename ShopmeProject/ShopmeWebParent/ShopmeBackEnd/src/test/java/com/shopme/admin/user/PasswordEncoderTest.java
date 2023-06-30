package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shopme.admin.security.ShopmeUserDetails;

public class PasswordEncoderTest {

	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "minh2001";
		String encodedPassword =  passwordEncoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
	}
	
}
