package com.gg_pigs.app.posterType.repository;

import com.gg_pigs.app.posterType.entity.PosterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosterTypeRepository extends JpaRepository<PosterType, Long> {

    Optional<PosterType> findPosterTypeByType(String type);
}
