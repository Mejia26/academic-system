package management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import management_system.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Used during login to find the user by their email
    Optional<User> findByEmail(String email);
    
    // Useful for validation before creating a new user
    boolean existsByEmail(String email);
}