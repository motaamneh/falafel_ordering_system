package com.motaamneh.falafel.repository;

import com.motaamneh.falafel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    // For admin - filter users by enabled status
    List<User> findByIsEnabled(Boolean isEnabled);

    // Check if email already exists (for signup validation)
    boolean existsByEmail(String email);


    Integer id(Integer id);
}
