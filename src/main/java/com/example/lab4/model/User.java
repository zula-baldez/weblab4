package com.example.lab4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="USERS")
public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column
    @Getter
    @Setter
    private String login;
    @Column
    @Getter
    @Setter
    private String password;
}
