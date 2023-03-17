package com.example.querydsl;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class TeamSlimDto {

    private Long id;

    private String name;

    @QueryProjection
    public TeamSlimDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
