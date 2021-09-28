package com.example.entity;

import java.util.Date;

public interface BoardProjection {
    long getNo();

    int getHit();

    String getTitle();

    String getWriter();

    Date getRegdate();
}
