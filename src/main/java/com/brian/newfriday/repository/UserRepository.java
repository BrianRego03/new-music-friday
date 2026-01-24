package com.brian.newfriday.repository;

import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(
            """
                    SELECT a from Album a
                    JOIN a.artistSet artist
                    JOIN artist.userSet user
                    WHERE user.id = :userId
                    AND (:startDate IS NULL OR a.releaseDate >= :startDate)
                    AND (:endDate IS NULL OR a.releaseDate <= :endDate)
                    ORDER BY a.releaseDate DESC
                  
                    """
    )
    List<Album> getAlbumsByUserIdAndReleaseDateBetween(
            @Param("userId") int userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
