package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "ITEM")
@SequenceGenerator(name = "SEQ1", sequenceName = "SEQ_ITEM_NO", initialValue = 1, allocationSize = 1)
public class Item {

    @Id // 기본키
    @Column(name = "NO") // 컬럼명 : Number
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ1") // 시퀀스의 값으로 추가됨
    private Long no;

    @Column(name = "NAME", length = 100) // 물품명 VARCHAR2(100)
    private String name;

    @Lob // String 이면 clob, byte[]이면 blob
    @Column(name = "CONTNET") // CLOB 길이가 아주 긴 문자열
    private String content;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "QUANTITY")
    private Long quantity;

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @ManyToOne // 판매자정보
    @JoinColumn(name = "SELLER")
    private Member member;
}
