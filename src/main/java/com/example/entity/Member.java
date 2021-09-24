package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "MEMBER")

public class Member {
    @Id
    @Column(name = "USERID")
    private String userid = null;
    @Column(name = "USERPW")
    private String userpw = null;

    @Transient // 컬럼이 안만들어짐
    private String userpw1 = null;
    @Column(name = "USERNAME")
    private String username = null;
    @Column(name = "USERTEL")
    private String usertel = null;
    @Column(name = "USERROLE")
    private String userrole = null;
    @Column(name = "USERDATE")
    private Date userdate = null;
    // 1 : N의 관계이고, Address 엔티티의 member변수와 매핑됨
    // @OneToMany(mappedBy = "member")
    // @OrderBy("no asc")
    // private List<Address> addressList = new ArrayList<>();
}
