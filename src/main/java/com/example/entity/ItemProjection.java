package com.example.entity;

import java.util.Date;

public interface ItemProjection {

    // 참고 : Item.java엔티티를 참고함
    // 필요한 get변수만 설정
    Long getNo();

    String getName();

    Long getPrice();

    Long getQuantity();

    Date getRegdate();
}
