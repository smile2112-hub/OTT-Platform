package com.cdac.flowflix.dto;

import com.cdac.flowflix.model.Role;
import com.cdac.flowflix.model.User;

public class ProfileDTO {

    private Long id;

    private String username;

    private String nameSurname;

    private String email;

    private String profilePicture;

    private Role role;

    private boolean deleted;

    public ProfileDTO() {

    }

    public ProfileDTO(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.nameSurname = user.getNameSurname();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.role = user.getRole();
        this.deleted = user.isDeleted();

    }

    // ==========================================
    // ID
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ==========================================
    // USERNAME
    // ==========================================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ==========================================
    // NAME
    // ==========================================

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    // ==========================================
    // EMAIL
    // ==========================================

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ==========================================
    // PROFILE PICTURE
    // ==========================================

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // ==========================================
    // ROLE
    // ==========================================

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // ==========================================
    // DELETED
    // ==========================================

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}