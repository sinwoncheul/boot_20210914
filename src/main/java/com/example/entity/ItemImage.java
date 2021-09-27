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
@Table(name = "ITEMIMAGE")
@SequenceGenerator(name = "SEQ1", sequenceName = "SEQ_ITEMIMAGE_NO", initialValue = 1, allocationSize = 1)

public class ItemImage {
    @Id
    @Column(name = "NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ1")
    private Long no;

    @Lob
    @Column(name = "IMAGE")
    private byte[] image = null;

    @Column(name = "IMAGENAME")
    private String imagename = null; // 파일명

    @Column(name = "IMAGESIZE", nullable = false, columnDefinition = "long default 0") // 기본값 0으로 설정
    private Long imagesize;

    @Column(name = "IMAGETYPE")
    private String imagetype = null; // 파일종류

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    // Item의 기본키 정보를 외래키로 연결
    @ManyToOne
    @JoinColumn(name = "ITEM")
    private Item item;
}
