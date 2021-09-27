package com.example.repository;

import java.util.List;

import com.example.entity.ItemImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    // private Item item;
    List<ItemImage> findByItem_No(long no);
}
