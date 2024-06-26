package com.spring.jpastudy.chap02.repository;

import com.spring.jpastudy.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 쿼리메서드: 메서드 이름에 특별한 규칙을 적용하면 SQL 이 규칙에 맞게 생성됨.
    // List<Student> findByStuName(String name); // (X)
    List<Student> findByName(String name);

    List<Student> findByCityAndMajor(String city, String major);

    // WHERE major LIKE '%major%'
    List<Student> findByMajorContaining(String major);
    // WHERE major LIKE 'major%'
    List<Student> findByMajorStartingWith(String major);
    // WHERE major LIKE '%major'
    List<Student> findByMajorEndingWith(String major);

    // WHERE age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

    // native sql 사용하기
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :snm OR city = :city"
            , nativeQuery = true)
    List<Student> getStudentByNameOrCity(@Param("snm") String name, @Param("city") String city);

    // getStudentByNameOrCity 와 똑같음
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = ?1 OR city = ?2"
            , nativeQuery = true)
    List<Student> getStudentByNameOrCity2(String name, String city);

    /*
        - JPQL

        SELECT 엔터티 별칭
        FROM 엔터티클래스명 AS 엔터티별칭
        WHERE 별칭.필드명

        ex) native - SELECT * FROM tbl_student WHERE stu_name = ?
            JPQL   - SELECT st FROM Student AS st WHERE st.name = ?
     */

    // 도시명으로 학생 1명을 단일 조회
    // Optional<> -> null 방지
    @Query(value = "SELECT st FROM Student st WHERE st.city = ?1")
    Optional<Student> getByCityWithJPQL(String city);

    // 특정 이름이 포함된 학생 리스트 조회하기
    @Query("SELECT stu FROM Student stu WHERE stu.name LIKE %?1%")
    List<Student> searchByNameWithJPQL(String name);

    // JPQL 로 갱신 처리하기
    @Modifying // SELECT 문이 아니면 @Modifying 무조건 추가
    @Query("DELETE FROM Student s WHERE s.name = ?1 AND s.city = ?2")
    void deleteByNameAndCityWithJPQL(String name, String city);
}
