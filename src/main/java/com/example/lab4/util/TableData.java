package com.example.lab4.util;

import com.example.lab4.model.Attempt;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TableData{
    @Getter
    @Setter
    private List<Attempt> attemptList;

    public TableData(List<Attempt> attemptList) {
        this.attemptList = attemptList;
    }
    public TableData() {
    }
}
