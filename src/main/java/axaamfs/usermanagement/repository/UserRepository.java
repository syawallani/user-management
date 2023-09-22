package axaamfs.usermanagement.repository;

import axaamfs.usermanagement.dto.UserResponse;
import axaamfs.usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByToken(String token);

    Optional<User> findByUsername(String username);

    @Query("""
            SELECT new axaamfs.usermanagement.dto.UserResponse(u.username, u.name) 
            FROM User u
            WHERE u.username LIKE %:filter% or u.name LIKE %:filter%
            """)
    Page<UserResponse> findAllUser(@Param("filter") String filter, Pageable pageable);
}
