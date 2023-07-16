package com.tinyblu.backend.api;

import com.tinyblu.backend.business.UserBusiness;
import com.tinyblu.backend.exception.BaseException;
import com.tinyblu.backend.exception.FileException;
import com.tinyblu.backend.model.MLoginRequest;
import com.tinyblu.backend.model.MRegisterRequest;
import com.tinyblu.backend.model.MRegisterResponse;
import com.tinyblu.backend.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MLoginRequest request) throws BaseException {
        String response = business.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {
        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws FileException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}
