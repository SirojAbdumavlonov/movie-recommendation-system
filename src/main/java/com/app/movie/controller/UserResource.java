package com.app.movie.controller;

import com.app.movie.domain.User;
import com.app.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Transactional
@RequiredArgsConstructor
public class UserResource {

    private final UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<User> createUsers(@RequestBody User user) throws URISyntaxException, BadRequestException {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID");
        }
        user = userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + user.getId()))
            .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsers(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody User user
    ) throws BadRequestException {
        log.debug("REST request to update User : {}, {}", id, user);
        if (user.getId() == null) {
            throw new BadRequestException("Invalid id");
        }
        if (!Objects.equals(id, user.getId())) {
            throw new BadRequestException("Invalid ID");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        user = userRepository.save(user);
        return ResponseEntity.ok()
            .body(user);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        log.debug("REST request to get all User");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsers(@PathVariable("id") Integer id) {
        log.debug("REST request to get User : {}", id);
        Optional<User> users = userRepository.findById(id);
        return ResponseEntity.ok(users.orElse(new User()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable("id") Integer id) {
        log.debug("REST request to delete User : {}", id);
        userRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
