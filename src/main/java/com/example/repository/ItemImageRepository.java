package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.ItemImage;
import com.example.entity.ItemProjection2;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItem_No(long no);

    // 물품별 고유한 번호 가져오기
    @Query(value = "select DISTINCT(item) item from ITEMIMAGE", nativeQuery = true)
    List<ItemProjection2> queryDistinctItem();

}
