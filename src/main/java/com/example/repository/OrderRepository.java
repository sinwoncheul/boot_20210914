package com.example.repository;

import com.example.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepsoitory extends JpaRepository<Order, String> {

}
