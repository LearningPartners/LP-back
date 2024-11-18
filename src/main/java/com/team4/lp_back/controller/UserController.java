package com.team4.lp_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.lp_back.dto.UserDTO;
import com.team4.lp_back.entity.UserEntity;
import com.team4.lp_back.security.TokenProvider;
import com.team4.lp_back.service.UserService;

import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime; // LocalDate 및 LocalDateTime 임포트

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 암호화

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value.");
            }

            UserEntity user = UserEntity.builder()
                    .userType(userDTO.getUserType())
                    .userName(userDTO.getUserName())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .name(userDTO.getName())
                    .email(userDTO.getEmail())
                    .phone(userDTO.getPhone())
                    .address(userDTO.getAddress())
                    .gender(userDTO.getGender())
                    .age(userDTO.getAge())
                    .birth(LocalDate.parse(userDTO.getBirth()))
                    .profileUrl(userDTO.getProfileUrl())
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            UserEntity registeredUser = userService.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                    .userId(registeredUser.getUserId())  // 등록 후 생성된 userId 사용
                    .userType(registeredUser.getUserType())
                    .userName(registeredUser.getUserName())
                    .name(registeredUser.getName())
                    .email(registeredUser.getEmail())
                    .phone(registeredUser.getPhone())
                    .address(registeredUser.getAddress())
                    .gender(registeredUser.getGender())
                    .age(registeredUser.getAge())
                    .birth(registeredUser.getBirth().toString())
                    .profileUrl(registeredUser.getProfileUrl())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (RuntimeException e) {
            // 중복된 사용자 이름에 대한 예외 처리
            if ("Username already exists".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = userService.getByCredentials(userDTO.getUserName(), userDTO.getPassword(), passwordEncoder);

            if (user != null) {
                final String token = tokenProvider.create(user);

                final UserDTO responseUserDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .name(user.getName())
                    .token(token)
                    .build();
                return ResponseEntity.ok().body(responseUserDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid ID or password");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing the request");
        }
    }
}
