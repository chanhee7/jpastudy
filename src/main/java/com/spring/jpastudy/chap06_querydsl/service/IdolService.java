package com.spring.jpastudy.chap06_querydsl.service;

import com.spring.jpastudy.chap06_querydsl.entity.Idol;
import com.spring.jpastudy.chap06_querydsl.repository.IdolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional // JPA , QueryDsl 을 쓸때는 붙여야 함
public class IdolService {

    private final IdolRepository idolRepository;

    // 아이돌을 나이 순으로 내림차 정렬해서 조회
    public List<Idol> getIdols() {
        // 1번째 방법
//        List<Idol> idolList = idolRepository.findAll();
//        return idolList.stream()
//                .sorted(Comparator.comparing(Idol::getAge).reversed())
//                .collect(Collectors.toList());

        // 2번째 방법
//        List<Idol> idolList = idolRepository.findAllBySorted();
//        return idolList;

        // 3번째
        List<Idol> idolList = idolRepository.foundByGroupName();
        return idolList;
    }

}
