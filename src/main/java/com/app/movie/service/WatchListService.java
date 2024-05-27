package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.User;
import com.app.movie.domain.WatchList;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.WatchListRepository;
import com.app.movie.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final MovieRepository movieRepository;

    public List<WatchList> getUserWatchLists() {
        log.debug("Getting user watch-lists");
        Integer userId = SecurityUtils.getCurrentUserPrincipal().getId();
        log.debug("Getting user watch-lists");
        return watchListRepository.getWatchListByUserId(userId);
    }

    public WatchList addMovieToWatchList(Integer listId, Long movieId) throws Exception {
        log.debug("Adding movie with ID {} to watch list with ID {}", movieId, listId);

        WatchList watchList = getWatchListElseThrow(listId);
        log.debug("Found watch list: {}", watchList);

        Movie movie = getMovieElseThrow(movieId);
        log.debug("Found movie : {}", movie);

        if (watchList.getMovies().contains(movie)) {
            log.error("Movie with ID {} is already in watch list with ID {}", movieId, listId);
            throw new BadRequestException("Movie is already in the watch list");
        }

        WatchList updatedWatchList = watchList.addMovie(movie);
        watchListRepository.save(updatedWatchList);

        log.debug("Added movie to watch list. Updated watch list: {}", updatedWatchList);
        return watchList;
    }

    private Movie getMovieElseThrow(Long movieId) throws BadRequestException {
        return movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("Movie with specified id not found"));
    }

    public WatchList removeMovieFromWatchList(Integer listId, Long movieId) throws Exception {
        log.debug("Removing movie with ID {} from watch list with ID {}", movieId, listId);

        WatchList watchList = getWatchListElseThrow(listId);

        Movie movie = getMovieElseThrow(movieId);
        log.debug("Found movie: {}", movie);

        if (!watchList.getMovies().contains(movie)) {
            log.error("Movie with ID {} not in watch list with ID {}", movieId, listId);
            throw new BadRequestException("Movie is not in watch list");
        }

        WatchList updatedWatchList = watchList.addMovie(movie);
        watchListRepository.save(updatedWatchList);

        log.debug("Removed movie from watch list. Updated watch list: {}", updatedWatchList);
        return watchList;
    }

    public Optional<WatchList> getOne(Integer id) {
        return watchListRepository.findById(id);
    }

    public WatchList saveWatchList(WatchList watchList) {
        return watchListRepository.save(watchList);
    }

    public WatchList updateWatchList(WatchList watchList) throws BadRequestException {
        if (!watchListRepository.existsById(watchList.getId())) {
            throw new BadRequestException("WatchList not found");
        }
        return watchListRepository.save(watchList);
    }

    public void deleteWatchList(Integer id) throws Exception {
        WatchList watchList = getWatchListElseThrow(id);
        if (!watchListRepository.existsById(watchList.getId())) {
            throw new BadRequestException("WatchList not found");
        }
        watchListRepository.deleteById(watchList.getId());
    }

    private WatchList getWatchListElseThrow(Integer listId) throws BadRequestException {
        return watchListRepository.findById(listId)
            .orElseThrow(() -> new BadRequestException("Watch-list with specified id not found"));
    }
}

