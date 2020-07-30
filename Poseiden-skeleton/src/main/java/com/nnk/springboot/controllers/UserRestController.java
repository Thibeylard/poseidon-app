package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Validated // Inexplicably, validation wont work without this annotation
@RepositoryRestController
public class UserRestController {
    private final UserRestRepository userRestRepository;
    private final PasswordEncoder encoder;
    private final RepositoryEntityLinks entityLinks;

    @Autowired
    public UserRestController(UserRestRepository userRestRepository, PasswordEncoder encoder, RepositoryEntityLinks entityLinks) {
        this.userRestRepository = userRestRepository;
        this.encoder = encoder;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ResponseEntity<?> registrateUser(@Valid @RequestBody User body) {
        body.setPassword(this.encoder.encode(body.getPassword()));

        User savedUser = userRestRepository.save(body);
        Resource<User> resource = new Resource<>(savedUser);
        resource.add(entityLinks.linkToSingleResource(User.class, savedUser.getId()).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
}
