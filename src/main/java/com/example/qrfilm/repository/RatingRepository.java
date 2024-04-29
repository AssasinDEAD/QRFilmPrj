package com.example.qrfilm.repository;

import com.example.qrfilm.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.fid = ?1")
    Double findAverageRatingByFilmId(Long filmId);

    @Query("SELECT r FROM Rating r WHERE r.uid = ?1 AND r.fid = ?2")
    Rating findByUidAndFid(Long userId, Long filmId);
}
