package com.lld.bookmyshow.repositories;

import com.lld.bookmyshow.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Picture, Long> {
    @Override
    Optional<Picture> findById(Long aLong);
}
