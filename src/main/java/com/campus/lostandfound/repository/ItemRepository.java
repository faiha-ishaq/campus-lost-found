package com.campus.lostandfound.repository;

import com.campus.lostandfound.model.Item;
import com.campus.lostandfound.model.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(ItemStatus status);
    List<Item> findByCategoryIgnoreCase(String category);
}