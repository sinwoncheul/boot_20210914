package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "ORDER1") // ORDER은 예약어 ORDER BY NO DESC
@SequenceGenerator(name = "SEQ_ORDER_NO", sequenceName = "SEQ_ORDER_NO", initialValue = 1, allocationSize = 1)
public class Order {
    @Id // 기본키
    @Column(name = "NO") // 주문번호
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_NO") // 시퀀스의 값으로 추가됨
    private Long no;

    @Column(name = "CNT")
    private Long cnt; // 주문수량

    @ManyToOne // 판매자 정보
    @JoinColumn(name = "MEMBER_USERID")
    private Member member;

    @ManyToOne // 물품 정보
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @Transient
    private Long itemno;
}
