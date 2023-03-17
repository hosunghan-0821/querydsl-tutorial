package com.example.querydsl;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.querydsl.QMember.*;
import static com.example.querydsl.QTeam.team;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;


    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before(){

        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

    }
    @Test
    void contextLoads() {

        hello hello = new hello();
        em.persist(hello);
        Qhello qhello = new Qhello("h");
        JPAQueryFactory query = new JPAQueryFactory(em);

        hello result =query.selectFrom(qhello)
                .fetchOne();

        System.out.println(hello);
        System.out.println(result);


    }

    @Test
    public void startQueryDsl(){


        Member member1 = queryFactory.selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

         QueryResults<Member> memberQueryResults = queryFactory
                 .selectFrom(member)
                 .fetchResults();

         List<Member> results = memberQueryResults.getResults();
        for (Member result : results) {

            System.out.println(result.getAge());
        }
    }

    @Test
    public void sort(){
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

         List<Member> fetch = queryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();


        for (Member fetch1 : fetch) {
            System.out.println();
        }
        assertEquals(fetch.get(0).getUsername(),"member5");

    }

    @Test
    public void paging1(){

         QueryResults<Member> fetch = queryFactory.selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        System.out.println(fetch.getResults().size());
    }

    @Test
    public void countPagigng(){
        Pageable pageable = (Pageable) new PageImpl<>(new ArrayList<>(), PageRequest.of(10, 20, Sort.unsorted()),20);
        List<Member> content = queryFactory
                .selectFrom(member)
                .offset(pageable.getOffset()) // offset
                .limit(pageable.getPageSize()) // limit
                .fetch(); // fetchResults() 대신 fetch 사용

    }

    @Test
    public void join(){
        queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(member.team.name.eq("teamA"))
                .fetch();
    }

    @Test
    public void joinOnFiltering(){
         List<Tuple> teamA = queryFactory.
                select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA")).fetch();

        for (Tuple tuple : teamA) {
            System.out.println("tuple : "+ tuple);
        }
    }

    @Test
    public void fetchJoinNo(){
        em.flush();
        em.clear();

        queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

    }
    @Test
    public void fetchJoinYes(){
        em.flush();
        em.clear();

        queryFactory
                .selectFrom(member)
                .join(member.team,team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

    }
}
