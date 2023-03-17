package com.example.querydsl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberTeamClassDto {


    private Long memberId;
    private String username;
    private int age;
    @JsonProperty(value = "team")
    private TeamSlimDto teamSlimDto;

    @QueryProjection
    public MemberTeamClassDto(Long memberId, String username, int age, TeamSlimDto teamSlimDto) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.teamSlimDto = teamSlimDto;
    }
}
