package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> personList = userRepository.findAll();
        return personList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    public void insert(User user) {
        userRepository.save(user);
    }

    public UserDTO update(UUID id, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
        }

        User existingUser = userOptional.get();
        User updatedUser = UserBuilder.toEntity(updatedUserDTO);

        // Update the fields of the existing user with the values from the updated user.
        existingUser.setName(updatedUser.getName());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        existingUser = userRepository.save(existingUser);

        return UserBuilder.toUserDTO(existingUser);
    }

    public User delete(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in the database", id);
            // You can throw an exception or return an appropriate response here.
        }

        userRepository.delete(userOptional.get());
        LOGGER.debug("User with id {} was deleted from the database", id);

        return userOptional.get();
    }

    public UserDTO findUserByNameAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByNameAndPassword(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserBuilder.toUserDTO(user);
        } else {
            // Handle the case where the user is not found or the password is incorrect
            return null;
        }
    }

    public UserDTO findUserByName(String name) {
        Optional<User> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {
            return UserBuilder.toUserDTO(userOptional.get());
        } else {
            LOGGER.error("User with name {} was not found in the database", name);
            // You can throw an exception or return an appropriate response here.
            return null;
        }
    }

}
