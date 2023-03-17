package com.example.querydsl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class testController {

    private final QueryDslTest queryDslTest;

    @GetMapping("/")
    public List<MemberTeamClassDto> test(){
        return queryDslTest.testDtoGet();
    }
}
