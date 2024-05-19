package com.app.movie.controller;

import com.app.movie.domain.WatchList;
import com.app.movie.repository.WatchListRepository;
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

    private final WatchListRepository watchListRepository;

    @PostMapping("/watch-lists")
    public ResponseEntity<WatchList> createWatchList(@RequestBody WatchList watchList) throws Exception {
        log.debug("REST request to save WatchList : {}", watchList);
        if (watchList.getId() != null) {
            throw new BadRequestException("A new watchList cannot already have an ID");
        }
        watchList = watchListRepository.save(watchList);
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

        if (!watchListRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        watchList = watchListRepository.save(watchList);
        return ResponseEntity.ok()
            .body(watchList);
    }

    @GetMapping("/watch-lists")
    public List<WatchList> getAllWatchLists() {
        log.debug("REST request to get all WatchLists");
        return watchListRepository.findAll();
    }

    @GetMapping("/watch-lists/{id}")
    public ResponseEntity<WatchList> getWatchList(@PathVariable("id") Integer id) {
        log.debug("REST request to get WatchList : {}", id);
        Optional<WatchList> watchList = watchListRepository.findById(id);
        return ResponseEntity.ok(watchList.orElseThrow());
    }


    @DeleteMapping("/watch-lists/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable("id") Integer id) {
        log.debug("REST request to delete WatchList : {}", id);
        watchListRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
