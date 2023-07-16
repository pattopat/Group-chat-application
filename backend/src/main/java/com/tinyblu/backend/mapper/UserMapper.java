package com.tinyblu.backend.mapper;

import com.tinyblu.backend.entity.User;
import com.tinyblu.backend.model.MRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toRegisterResponse(User user);
}


