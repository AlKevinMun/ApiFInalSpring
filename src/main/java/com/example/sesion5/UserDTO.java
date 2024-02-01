package com.example.sesion5;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String fullName;

    public UserDTO() {
    }

    public UserDTO(Integer id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }

}
