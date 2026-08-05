package com.skillforge.skillforge_monolith.repository;

import com.skillforge.skillforge_monolith.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.skills WHERE u.id = :id")
    Optional<User> findByIdWithSkills(@Param("id") String id);
}
