package com.app.movie.controller;

import com.app.movie.domain.Authority;
import com.app.movie.repository.AuthorityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/authorities")
@Transactional
@RequiredArgsConstructor
public class AuthorityResource {

    private final AuthorityRepository authorityRepository;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Authority> createAuthority(@Valid @RequestBody Authority authority) throws Exception {
        log.debug("REST request to save Authority : {}", authority);
        if (authorityRepository.existsById(authority.getName())) {
            throw new BadRequestException("authority already exists");
        }
        authority = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("/api/authorities/" + authority.getName()))
            .body(authority);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Authority> getAllAuthorities() {
        log.debug("REST request to get all Authorities");
        return authorityRepository.findAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Authority> getAuthority(@PathVariable("id") String id) {
        log.debug("REST request to get Authority : {}", id);
        Authority authority = authorityRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(authority);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAuthority(@PathVariable("id") String id) {
        log.debug("REST request to delete Authority : {}", id);
        authorityRepository.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
