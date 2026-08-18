package user_service.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import user_service.dto.UserDto;

@Service
public class UserService {

    private final Map<Long, UserDto> users = Map.of(
            1L, new UserDto(1L, "Sangeetha", "sangeetha@gmail.com"),
            2L, new UserDto(2L, "Priya", "priya@gmail.com")
    );

    public UserDto getUserById(Long id) {
        return users.get(id);
    }
}