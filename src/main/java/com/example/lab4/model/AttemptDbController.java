package com.example.lab4.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptDbController  extends CrudRepository<Attempt, Long> {

    List<Attempt> findAllByAuthorIdEquals(Long id, Pageable pageable);
    Long countByAuthorIdEquals(Long id);
    Attempt findTopByAuthorIdEqualsOrderByAttemptDesc(long authorId);
/*
    Attempt findTopByOrderByAttemptDesc();
*/

}
