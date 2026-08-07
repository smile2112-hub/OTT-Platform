package com.cdac.flowflix.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameSurname;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String profilePicture;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Projection> projections =
            new ArrayList<>();

    // ==========================================
    // USER SUBSCRIPTION
    // ==========================================

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private Subscription subscription;

    public User() {

    }

    public User(
            Long id,
            String nameSurname,
            String username,
            String password,
            String email,
            String profilePicture,
            boolean deleted,
            Role role,
            List<Projection> projections,
            Subscription subscription) {

        this.id = id;
        this.nameSurname = nameSurname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilePicture = profilePicture;
        this.deleted = deleted;
        this.role = role;
        this.projections = projections;
        this.subscription = subscription;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getNameSurname() {

        return nameSurname;

    }

    public void setNameSurname(String nameSurname) {

        this.nameSurname = nameSurname;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getProfilePicture() {

        return profilePicture;

    }

    public void setProfilePicture(String profilePicture) {

        this.profilePicture = profilePicture;

    }

    public boolean isDeleted() {

        return deleted;

    }

    public void setDeleted(boolean deleted) {

        this.deleted = deleted;

    }

    public Role getRole() {

        return role;

    }

    public void setRole(Role role) {

        this.role = role;

    }

    public List<Projection> getProjections() {

        return projections;

    }

    public void setProjections(List<Projection> projections) {

        this.projections = projections;

    }

    public Subscription getSubscription() {

        return subscription;

    }

    public void setSubscription(Subscription subscription) {

        this.subscription = subscription;

    }

}