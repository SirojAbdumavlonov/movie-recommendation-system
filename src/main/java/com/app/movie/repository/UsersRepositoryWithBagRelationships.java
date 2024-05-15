package com.app.movie.repository;
import java.util.List;
import java.util.Optional;

import com.app.movie.domain.Users;
import org.springframework.data.domain.Page;

public interface UsersRepositoryWithBagRelationships {
    Optional<Users> fetchBagRelationships(Optional<Users> users);

    List<Users> fetchBagRelationships(List<Users> users);

    Page<Users> fetchBagRelationships(Page<Users> users);
}
