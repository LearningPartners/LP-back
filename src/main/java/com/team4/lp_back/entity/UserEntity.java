package com.team4.lp_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;             // Auto-increment primary key

    @Column(nullable = false)
    private String userType;        // Type of user

    @Column(nullable = false, unique = true)
    private String userName;        // Unique username

    @Column(nullable = false)
    private String password;        // Encrypted password

    @Column(nullable = false)
    private String name;            // Name of user

    @Column(nullable = false, unique = true)
    private String email;           // Email address

    @Column(nullable = false, unique = true)
    private String phone;           // Phone number

    @Column(nullable = false)
    private String address;         // Address

    @Column(nullable = false, length = 3)
    private String gender;          // Gender, e.g., "여"

    @Column(nullable = false)
    private Integer age;            // Age

    @Column(nullable = true)
    private LocalDate birth;        // Birth date

    @Column(nullable = true)
    private String profileUrl;      // Profile image URL

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;  // Creation date

    @Column(nullable = false)
    private LocalDateTime updateDate;  // Update date
}
