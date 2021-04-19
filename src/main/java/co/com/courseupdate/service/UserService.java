package co.com.courseupdate.service;

import co.com.courseupdate.data.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> getUserContainingKeyword(String keyword);

    Optional<UserDto> findOneById(long id);

    Iterable<UserDto> findAll();
}
