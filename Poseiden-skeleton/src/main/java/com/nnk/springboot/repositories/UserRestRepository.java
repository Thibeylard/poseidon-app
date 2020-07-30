package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRestRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    //    @PreAuthorize("permitAll()")
    @RestResource(exported = false)
    Optional<User> findByUsername(String username);
}
