package com.example.lab4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "attempt")
public class Attempt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;
    @Column
    private Integer attempt = 0;
    @Column
    private Double x;
    @Column
    private Double y;
    @Column
    private Double r;
    @Column
    private Boolean hit;
    @Column
    private Long workTime;
    @Column
    private Long startTime;
    @Column
    private Long authorId;

    public void configAttempt(int attempt, double x, double y, double r, boolean hit, Long workTime, Long startTime, Long authorId) {
        setAttempt(attempt);
        setX(x);
        setY(y);
        setR(r);
        setHit(hit);
        setWorkTime(workTime);
        setStartTime(startTime);
        setAuthorId(authorId);
    }



}

