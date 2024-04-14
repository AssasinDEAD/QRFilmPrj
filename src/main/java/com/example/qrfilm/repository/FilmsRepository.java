package com.example.qrfilm.repository;
import com.example.qrfilm.entity.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmsRepository extends JpaRepository<Films, Long> {
}
