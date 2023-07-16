package com.tinyblu.backend.repository;

import com.tinyblu.backend.entity.Social;
import com.tinyblu.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social, String> {

    Optional<Social> findByUser(User user);

}
