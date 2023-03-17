package com.example.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.querydsl.QMember.member;
import static com.example.querydsl.QTeam.team;

@RequiredArgsConstructor
@Repository
public class QueryDslTest {
    private final  EntityManager em;


    @Transactional
    public List<MemberTeamClassDto> testDtoGet(){
         Member asd = new Member("asd", 123);
         Team team1 = new Team("Asd");
         em.persist(team1);
         em.persist(asd);
         asd.changeTeam(team1);



        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        List<MemberTeamDto> memberList = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                        ))
                .from(member)
                .leftJoin(member.team, team)
                .fetch();


        //Dto 조합할 대 유용할거 같은데?

         List<MemberTeamClassDto> testasd = queryFactory.select(new QMemberTeamClassDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        new QTeamSlimDto(
                                member.team.id,
                                member.team.name
                        )

                ))
                .from(member)
                .leftJoin(member.team, team)
                .fetch();

        for (MemberTeamClassDto memberTeamClassDto : testasd) {
            System.out.println("member "+ memberTeamClassDto);
        }
        return testasd;
    }
}
