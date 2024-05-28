package com.app.movie.controller;

import com.app.movie.domain.WatchList;
import com.app.movie.service.WatchListService;
import com.app.movie.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
@RequiredArgsConstructor
public class WatchListResource {

    private final WatchListService watchListService;

    @GetMapping("/watch-lists/{id}")
    public ResponseEntity<WatchList> getWatchList(@PathVariable("id") Integer id) throws Exception {
        log.debug("REST request to get WatchList : {}", id);
        Optional<WatchList> watchList = watchListService.getOne(id);
        ResponseEntity<WatchList> response = ResponseUtils.wrapOrNotFound(watchList);
        log.debug("REST response : {}", response);
        return response;
    }

    @PostMapping("/watch-lists/{listId}/{movieId}")
    public ResponseEntity<WatchList> addMovie(@PathVariable Integer listId, @PathVariable Long movieId) throws Exception {
        log.debug("REST request to add movie to watch-list. MovieId: {}. WatchListId: {}", movieId, listId);
        WatchList watchList = watchListService.addMovieToWatchList(listId, movieId);
        log.debug("Resulted WatchList: {}", watchList);
        return ResponseEntity.ok()
            .body(watchList);
    }

    @DeleteMapping("/watch-lists/{listId}/{movieId}")
    public ResponseEntity<WatchList> removeMovie(@PathVariable Integer listId, @PathVariable Long movieId) throws Exception {
        log.debug("REST request to remove movie from watch-list. MovieId: {}. WatchListId: {}", movieId, listId);
        WatchList watchList = watchListService.removeMovieFromWatchList(listId, movieId);
        log.debug("Resulted WatchList : {}", watchList);
        return ResponseEntity.ok()
            .body(watchList);
    }

    @PostMapping("/watch-lists")
    public ResponseEntity<WatchList> createWatchList(@RequestBody WatchList watchList) throws Exception {
        log.debug("REST request to save WatchList : {}", watchList);
        if (watchList.getId() != null) {
            throw new BadRequestException("A new watchList cannot already have an ID");
        }
        watchList = watchListService.saveWatchList(watchList);
        return ResponseEntity.created(new URI("/api/watch-lists/" + watchList.getId()))
            .body(watchList);
    }

    @PutMapping("/watch-lists/{id}")
    public ResponseEntity<WatchList> updateWatchList(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody WatchList watchList
    ) throws BadRequestException {
        log.debug("REST request to update WatchList : {}, {}", id, watchList);
        if (watchList.getId() == null) {
            throw new BadRequestException("Invalid watch-list id");
        }
        if (!Objects.equals(id, watchList.getId())) {
            throw new BadRequestException("Invalid watch-list ID");
        }

        watchList = watchListService.updateWatchList(watchList);
        return ResponseEntity.ok()
            .body(watchList);
    }

    @GetMapping("/watch-lists")
    public List<WatchList> getUserWatchLists() {
        log.debug("REST request to get all WatchLists");
        return watchListService.getUserWatchLists();
    }

    @DeleteMapping("/watch-lists/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable("id") Integer id) throws Exception {
        log.debug("REST request to delete WatchList : {}", id);
        watchListService.deleteWatchList(id);
        return ResponseEntity.noContent()
            .build();
    }
}
