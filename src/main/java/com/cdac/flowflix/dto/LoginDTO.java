package com.cdac.flowflix.dto;

public class LoginDTO {

    private String token;

    private String userNameSurname;

    private String messageInvalidUsernameOrPassword;

    public LoginDTO() {
    }

    public LoginDTO(String token,
                    String userNameSurname,
                    String messageInvalidUsernameOrPassword) {

        this.token = token;
        this.userNameSurname = userNameSurname;
        this.messageInvalidUsernameOrPassword =
                messageInvalidUsernameOrPassword;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserNameSurname() {
        return userNameSurname;
    }

    public void setUserNameSurname(String userNameSurname) {
        this.userNameSurname = userNameSurname;
    }

    public String getMessageInvalidUsernameOrPassword() {
        return messageInvalidUsernameOrPassword;
    }

    public void setMessageInvalidUsernameOrPassword(
            String messageInvalidUsernameOrPassword) {

        this.messageInvalidUsernameOrPassword =
                messageInvalidUsernameOrPassword;

    }

}