package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Example: JPA generate Query by Field
     */
    Optional<User> findByName(String name);

    /**
     * Example: Write Custom Query
     */
    @Query(value = "SELECT u " +
            "FROM User u " +
            "WHERE u.name = :name ")
    Optional<User> findUsersByName(@Param("name") String name);

    Optional<User> findByNameAndPassword(String name, String password);



}
