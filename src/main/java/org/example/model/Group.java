package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class Group {
    private int id;
    private String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
