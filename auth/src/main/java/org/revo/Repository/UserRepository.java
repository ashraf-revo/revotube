package org.revo.Repository;

import org.revo.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by ashraf on 17/04/17.
 */
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}
