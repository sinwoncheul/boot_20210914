package com.example.repository;

import java.util.List;

import com.example.entity.Item;
import com.example.entity.ItemProjection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<ItemProjection> findAllByOrderByNoAsc();
}
