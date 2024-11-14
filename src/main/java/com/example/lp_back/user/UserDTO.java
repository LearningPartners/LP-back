package com.example.lp_back.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String token;
    private Long usernum; // Match with Primary Key in UserEntity
    private String id;
    private String password;
    private String name;
}