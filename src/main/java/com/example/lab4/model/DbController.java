package com.example.lab4.model;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@ComponentScan
public interface DbController extends CrudRepository<User, Long> {
    public Optional<User> findByLogin(String login);
}