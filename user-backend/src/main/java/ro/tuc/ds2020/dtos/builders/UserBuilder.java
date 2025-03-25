package ro.tuc.ds2020.dtos.builders;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;

import java.util.Collections;
import java.util.UUID;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getPassword(), user.getRole());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getPassword(), userDTO.getRole());
    }

    // In UserBuilder class
    public static UserDetails toUserDetails(UserDTO userDTO) {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                userDTO.getName(),
                userDTO.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userDTO.getRole()))
        );
        return user;

    }


}
