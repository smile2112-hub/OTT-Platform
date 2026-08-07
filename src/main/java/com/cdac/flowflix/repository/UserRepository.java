package com.cdac.flowflix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ==========================================
    // LOGIN
    // ==========================================

    User findByUsername(String username);

    User findByEmail(String email);

    // ==========================================
    // ADMIN
    // ==========================================

    Long countByRole(Role role);

    List<User> findByRole(Role role);

    // ==========================================
    // ACTIVE / DELETED USERS
    // ==========================================

    List<User> findByDeletedFalse();

    List<User> findByDeletedTrue();

    Long countByDeletedFalse();

    Long countByDeletedTrue();

    // ==========================================
    // SEARCH
    // ==========================================

    List<User> findByNameSurnameContainingIgnoreCase(String nameSurname);

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByEmailContainingIgnoreCase(String email);

    // ==========================================
    // EXISTS
    // ==========================================

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}