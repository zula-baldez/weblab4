package com.example.lab4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
public class Attempt implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;
    @Column
    @Getter
    @Setter
    private Integer attempt = 0;
    @Column
    @Getter
    @Setter
    private Double x;
    @Column
    @Getter
    @Setter
    private Double y;
    @Column
    @Getter
    @Setter
    private Double r;
    @Column
    @Getter
    @Setter
    private Boolean hit;
    @Column
    @Getter
    @Setter
    private Long workTime;
    @Column
    @Getter
    @Setter
    private Long startTime;

    public Attempt() {
    }

    public Attempt(int attempt, double x, double y, double r, boolean hit, Long workTime, Long startTime) {
        this.attempt = attempt;
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.workTime = workTime;
        this.startTime = startTime;
    }


}

