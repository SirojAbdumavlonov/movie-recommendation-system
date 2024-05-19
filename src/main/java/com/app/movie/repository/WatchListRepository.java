package com.app.movie.repository;
import com.app.movie.domain.WatchList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {}
