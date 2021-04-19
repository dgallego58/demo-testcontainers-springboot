package co.com.courseupdate.service.server;

import co.com.courseupdate.data.UserDto;
import co.com.courseupdate.data.UserMapper;
import co.com.courseupdate.repo.UserRepository;
import co.com.courseupdate.service.UserService;
import io.vavr.collection.Array;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDto> getUserContainingKeyword(String keyword) {
        return userRepository.findByNameContaining(keyword).stream().findFirst().map(UserMapper.INSTANCE::toUserDto);
    }

    @Override
    public Optional<UserDto> findOneById(long id) {
        return userRepository.findById(id).map(UserMapper.INSTANCE::toUserDto);
    }

    @Override
    public Iterable<UserDto> findAll() {
        return Array.ofAll(userRepository.findAll()).map(UserMapper.INSTANCE::toUserDto);
    }
}
