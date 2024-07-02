package com.spring.jpastudy.event.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.event.entity.Event;
import com.spring.jpastudy.event.entity.QEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public List<Event> findEvents(String sort) {
        return factory
                .selectFrom(QEvent.event)
                .orderBy(specifier(sort))
                .fetch()
                ;
    }

    // 정렬 조건을 처리하는 메서드
    private OrderSpecifier<?> specifier(String sort) {
        switch (sort) {
            case "date":
                return QEvent.event.date.desc();
            case "title":
                return QEvent.event.title.asc();
            default:
                return null;
        }
    }
}
