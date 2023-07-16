package com.tinyblu.backend.business;

import com.tinyblu.backend.entity.User;
import com.tinyblu.backend.exception.BaseException;
import com.tinyblu.backend.exception.FileException;
import com.tinyblu.backend.exception.UserException;
import com.tinyblu.backend.mapper.UserMapper;
import com.tinyblu.backend.model.MLoginRequest;
import com.tinyblu.backend.model.MRegisterRequest;
import com.tinyblu.backend.model.MRegisterResponse;
import com.tinyblu.backend.service.TokenService;
import com.tinyblu.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {
    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public String login(MLoginRequest request) throws UserException {
        //validate request

        //verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();

        }

       return tokenService.tokenize(user);

        }


    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());

        return userMapper.toRegisterResponse(user);

    }

    public String uploadProfilePicture(MultipartFile file) throws FileException {


        if (file == null) {
            //throw error
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            //throw error
            throw FileException.unsupported();
        }
        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (supportedTypes.contains(contentType)) {
            //throw error
            throw FileException.unsupported();
        }

        try {
            byte[] pytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

}
