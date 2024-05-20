package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.WatchList;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.WatchListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final MovieRepository movieRepository;

    public WatchList addMovieToWatchList(Integer listId, Long movieId) throws Exception {
        log.debug("Adding movie with ID {} to watch list with ID {}", movieId, listId);

        WatchList watchList = watchListRepository.findById(listId)
            .orElseThrow(() -> new BadRequestException("Watch-list with specified id not found"));
        log.debug("Found watch list: {}", watchList);

        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("Movie with specified id not found"));
        log.debug("Found movie: {}", movie);

        if (watchList.getMovies().contains(movie)) {
            log.error("Movie with ID {} is already in watch list with ID {}", movieId, listId);
            throw new BadRequestException("Movie is already in the watch list");
        }

        WatchList updatedWatchList = watchList.addMovie(movie);
        watchListRepository.save(updatedWatchList);

        log.debug("Added movie to watch list. Updated watch list: {}", updatedWatchList);
        return watchList;
    }
}

