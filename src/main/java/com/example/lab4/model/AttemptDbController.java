package com.example.lab4.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptDbController  extends CrudRepository<Attempt, Long> {
}
