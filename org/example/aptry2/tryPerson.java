package org.example.aptry2;

import java.io.Serializable;

public class tryPerson implements Serializable {
    private String name;
    private String password;
    private String email;

    public tryPerson(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return email;
    }
}

