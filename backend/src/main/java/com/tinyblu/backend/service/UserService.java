package com.tinyblu.backend.service;

import com.tinyblu.backend.entity.User;
import com.tinyblu.backend.exception.BaseException;
import com.tinyblu.backend.exception.UserException;
import com.tinyblu.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional <User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public User updateName(String id, String name) throws UserException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()){
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setName(name);
        return repository.save(user);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
       return passwordEncoder.matches(rawPassword, encodedPassword);

    }

    public User create (String email, String password, String name) throws BaseException {

        //validate
        if (Objects.isNull(email)){
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(password)){
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(name)){
            throw UserException.createNameNull();
        }

        //verify
        if (repository.existsById(email)) {
            throw UserException.createEmailDuplicated();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        return repository.save(entity);

    }
}
