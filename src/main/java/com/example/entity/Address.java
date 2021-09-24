package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

@Table(name = "ADDRESS")
@SequenceGenerator(name = "SEQ_ADDRESS", sequenceName = "SEQ_ADDRESS_NO", initialValue = 1, allocationSize = 1)
public class Address {

    @Id
    @Column(name = "NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS")
    private long no; // 주소번호(기본키), 자동으로 숫자 추가됨.

    @Column(name = "ADDR", length = 200)
    private String addr; // 실제 주소

    @CreationTimestamp
    @Column(name = "REGDATE", updatable = false)
    private Date regdate; // 등록일

    @ManyToOne
    @JoinColumn(name = "MEMBER")
    private Member member; // N : 1의 관계
}
