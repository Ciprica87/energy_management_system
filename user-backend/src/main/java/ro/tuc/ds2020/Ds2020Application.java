package ro.tuc.ds2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.services.UserService;

import java.util.TimeZone;
import java.util.UUID;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    private UserRepository userRepository;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Ds2020Application.class, args);
    }
}
