package com.doopp.youlin.mapper;

import com.doopp.youlin.pojo.auth.AuthUser;
import com.doopp.youlin.pojo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "mobile", ignore = true),
            @Mapping(target = "estateIds", ignore = true),
            @Mapping(target = "createTime", ignore = true),
    })
    User user(AuthUser authUser);
}
