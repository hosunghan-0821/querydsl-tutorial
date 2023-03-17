package com.example.querydsl;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberTeamClassDto {


    private Long memberId;
    private String username;
    private int age;
    private TeamSlimDto team;

    @QueryProjection
    public MemberTeamClassDto(Long memberId, String username, int age, TeamSlimDto team) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.team = team;
    }
}
