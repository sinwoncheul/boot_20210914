package com.example.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface ItemProjection {
    Long getNo(); // 물품번호

    String getName(); // 물품명

    Long getPrice(); // 가격

    Long getQuantity(); // 수량

    Date getRegdate(); // 등록일

    // 엔티티의 JoinColumn으로 되어 있는 변수 Projection
    @Value("#{target.member}")
    Member getMember(); // 판매자 객체

    @Value("#{target.member.userid}")
    String getMemberUserid(); // 판매자의 아이디

    @Value("#{target.member.username}")
    String getMemberUsername(); // 판매자의 이름

}
