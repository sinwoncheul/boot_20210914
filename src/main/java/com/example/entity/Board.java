package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity // 엔티티 => 자동으로 테이블 생성
@Table(name = "BOARD1") // 테이블 명
@SequenceGenerator(name = "SEQ_BOARD1", sequenceName = "SEQ_BOARD1_NO", initialValue = 1, allocationSize = 1) // 시퀀스
                                                                                                              // 생성(자동으로
                                                                                                              // 증가되는
                                                                                                              // 번호)
public class Board {
    @Column(name = "NO") // 컬럼명
    @Id // 기본키(중복X)
    // 여기 추가되는 정보는 위에 생성한 시퀀스 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD1")
    private Long no = 0L; // 글번호

    @Column(name = "TITLE")
    private String title = null; // 글제목

    @Column(name = "CONTENT")
    private String content = null; // 내용

    @Column(name = "WRITER")
    private String writer = null; // 작성자

    @Column(name = "HIT")
    private int hit = 1; // 조회수

    @CreationTimestamp // 날짜는 자동으로 추가
    @Column(name = "REGDATE")
    private Date regdate = null; // 날짜
}
