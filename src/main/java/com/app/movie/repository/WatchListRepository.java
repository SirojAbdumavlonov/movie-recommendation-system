package com.app.movie.repository;
import com.app.movie.domain.WatchList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {
    List<WatchList> getWatchListByUserId(Integer userId);
}
