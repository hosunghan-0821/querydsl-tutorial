package com.example.querydsl;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamSlimDto {

    private Long id;

    private String name;

    @QueryProjection
    public TeamSlimDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
