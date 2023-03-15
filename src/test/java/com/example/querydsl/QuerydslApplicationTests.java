package com.example.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @Autowired
    EntityManager em;
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
        QMember m = new QMember("n");

    }

}
