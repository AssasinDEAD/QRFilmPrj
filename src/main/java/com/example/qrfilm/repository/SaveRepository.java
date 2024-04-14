package com.example.qrfilm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.qrfilm.entity.SaveData;
@Repository
public interface SaveRepository extends JpaRepository<SaveData, Long> {
}

