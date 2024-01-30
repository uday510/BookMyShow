package com.lld.bookmyshow;

import com.lld.bookmyshow.controllers.UserController;
import com.lld.bookmyshow.dto.SignupRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner  {
	private final UserController userController;
	@Autowired
    public BookMyShowApplication(UserController userController) {
        this.userController = userController;
    }

    public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		try {
			SignupRequestDTO signupRequestDTO = new SignupRequestDTO();
			signupRequestDTO.setEmail("udayteja510@icloud.com");
			signupRequestDTO.setPassword("password");
			userController.signUp(signupRequestDTO);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}
