package com.chivapchichi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Data
public class Users {

    @Id
    @Column(name = "user_name", nullable = false)
    @Email(message = "Не правильный формат email")
    private String userName;

    @Column(nullable = false)
    private String password;

    private String role;
}
