package com.spring.jpastudy.event.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.jpastudy.event.entity.Event;
import com.spring.jpastudy.event.entity.QEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EventRepositoryCustomImpl implements EventRepositoryCustom {

    private final JPAQueryFactory factory;

    @Override
    public Page<Event> findEvents(Pageable pageable, String sort) {

        // 페이징을 통한 조회
        List<Event> eventList =  factory
                .selectFrom(QEvent.event)
                .orderBy(specifier(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 데이터 수 조회
        Long count = factory
                .select(QEvent.event.count())
                .from(QEvent.event)
                .fetchOne();

        return new PageImpl<>(eventList, pageable, count);
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
