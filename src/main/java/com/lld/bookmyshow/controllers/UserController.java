package com.lld.bookmyshow.controllers;

import com.lld.bookmyshow.dto.ResponseStatus;
import com.lld.bookmyshow.dto.SignupRequestDTO;
import com.lld.bookmyshow.dto.SignupResponseDTO;
import com.lld.bookmyshow.models.User;
import com.lld.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignupResponseDTO signUp(SignupRequestDTO signupRequestDTO) {
        SignupResponseDTO responseDTO = new SignupResponseDTO();
        try {
            User user = userService.signup(signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            responseDTO.setUserId(user.getId());
        } catch (Exception exception) {
            throw new RuntimeException("Something went wrong.");
        }

        return responseDTO;
    }
}
