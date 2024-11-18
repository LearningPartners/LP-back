package com.team4.lp_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;          // Unique ID
    private String token;          // Token for authentication response
    private String userType;       // Type of user, e.g., "parent"
    private String userName;       // Unique username
    private String password;       // Encrypted password
    private String name;           // User's name
    private String email;          // Email address
    private String phone;          // Phone number
    private String address;        // Address
    private String gender;         // Gender, e.g., "여"
    private Integer age;           // Age
    private String birth;          // Birth date as a String
    private String profileUrl;     // URL to the profile image
}


