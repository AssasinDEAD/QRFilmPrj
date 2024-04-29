package com.example.qrfilm.repository;

import com.example.qrfilm.entity.SaveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SaveData, Long> {
}
