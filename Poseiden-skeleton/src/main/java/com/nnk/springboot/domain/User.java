package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.ValidPassword;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Users")
public @Data
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    protected User() {
    }

    public User(String fullname, String username, String password, String role) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.role = role;
    }


}
