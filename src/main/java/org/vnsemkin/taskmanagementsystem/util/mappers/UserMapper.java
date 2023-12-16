package org.vnsemkin.taskmanagementsystem.util.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vnsemkin.taskmanagementsystem.dto.UserDto;
import org.vnsemkin.taskmanagementsystem.entity.User;
import org.vnsemkin.taskmanagementsystem.exception.AppUserNotFoundException;
import org.vnsemkin.taskmanagementsystem.service.repo.UserRepoInterfaceImpl;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserMapper {
    private UserRepoInterfaceImpl userRepo;
    public static UserDto UserToUserDto(User user) {
        if (Objects.nonNull(user)) {
            return UserDto.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        }
        return new UserDto();
    }

    public  User UserDtoToUser(UserDto userDto) {
        if (Objects.nonNull(userDto)) {
            return userRepo.findByUsername(userDto.getUsername()).orElseThrow(
                    () -> new AppUserNotFoundException("User with username "
                            + userDto.getUsername()
                            + " doesnt exist."));
        }
        return new User();
    }
}
