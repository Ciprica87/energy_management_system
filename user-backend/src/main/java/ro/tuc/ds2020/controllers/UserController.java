package ro.tuc.ds2020.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.AuthResponseDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.security.JwtUtil;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("userDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /*@PostMapping("/insertUser")
    public ResponseEntity<UUID> insertUser(@Valid @RequestBody UserDTO userDTO) {
        UUID userId = userService.insert(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }*/

    @GetMapping(value = "/getUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userId) {
        UserDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID userId, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /*@DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @PostMapping("/addUserGlobal")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UUID> createUserAndNotifyEMService(@RequestBody UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        // Assuming user.getId() generates a new UUID for the user
        UUID userId = user.getId();

        String targetUrl = "http://172.30.1.2:8081/springg-demo/device/addUser/" + userId;

        ResponseEntity<UUID> emServiceResponse = restTemplate.postForEntity(
                targetUrl, null, UUID.class);

        if (emServiceResponse.getStatusCode() == HttpStatus.CREATED) {
            userService.insert(user);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> deleteUserAndNotifyEMService(@PathVariable UUID userId) {
        ResponseEntity<Void> emServiceResponse = restTemplate.exchange(
                "http://172.30.1.2:8081/springg-demo/device/deleteUser/{userId}",
                HttpMethod.DELETE,
                null,
                Void.class,
                userId
        );

        if (emServiceResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
            User user = userService.delete(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getName();
        String password = userDTO.getPassword();

        UserDTO loggedInUser = userService.findUserByNameAndPassword(username, password);
        if (loggedInUser != null) {
            final String token = jwtUtil.generateToken(UserBuilder.toUserDetails(loggedInUser));
            return ResponseEntity.ok(new AuthResponseDTO(loggedInUser, token));
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        // Validate the incoming data
        if (userDTO == null || userDTO.getName() == null || userDTO.getPassword() == null) {
            return new ResponseEntity<>("Invalid user data", HttpStatus.BAD_REQUEST);
        }

        try {
            // Check if user already exists
            UserDTO existingUser = userService.findUserByName(userDTO.getName());
            if (existingUser != null) {
                return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
            }

            // Insert the new user
            User user = UserBuilder.toEntity(userDTO);
            userService.insert(user);

            // Generate token
            final String token = jwtUtil.generateToken(UserBuilder.toUserDetails(userDTO));

            // Return the token and user details
            return ResponseEntity.ok(new AuthResponseDTO(userDTO, token));
        } catch (Exception e) {
            LOGGER.error("Error registering user: ", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


