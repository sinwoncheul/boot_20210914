package com.example.repository;

import com.example.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepsoitory extends JpaRepository<Member, String> {

}
