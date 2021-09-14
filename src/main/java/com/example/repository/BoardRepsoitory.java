package com.example.repository;

import com.example.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepsoitory extends JpaRepository<Board, Long> {

}
