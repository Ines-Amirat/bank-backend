package org.bank.entities;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity @Table(name = "users")
public class UserAccount {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false)
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    @Column(nullable = false)
    private String lastName;


    public UUID getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }



    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "password_hash")
    private String passwordHash;



}
