package com.spring.jpastudy.chap01.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode(of = "id")
//@EqualsAndHashCode(of = {"id", "name"}) // 필드명 id 랑 name 은 같은 객체란 뜻
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id // @Id -> PK값 표시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 추가
    @Column(name = "prod_id")
    private Long id; // PK

    // name -> 컬럼명 변경 /length = 30 -> VARCHAR(30) / nullable = false -> NOT NULL
    @Column(name = "prod_nm", length = 30, nullable = false)
    private String name; // 상품명

    @Column(name = "price")
    private int price; // 상품 가격

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // enum 사용하려면 EnumType.STRING 으로 하기
    private Category category; // 상품 카테고리

    @CreationTimestamp // INSERT 문 실행 시 자동으로 서버시간 저장
    @Column(updatable = false) // -> 수정불가
    private LocalDateTime createdAt; // 상품 등록시간

    @UpdateTimestamp // UPDATE 문 실행 시 자동으로 시간이 저장
    private LocalDateTime updatedAt; // 상품 수정 시간

    // 데이터베이스에는 저장안하고 클래스 내부에서만 사용할 필드
    @Transient
    private String nickName;

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

}
