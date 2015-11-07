package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nelson on 10/14/15.
 */


@Cacheable
@NamedQueries({
        @NamedQuery(name = "com.cs6310.backend.model.AccessCredential.findByUsername",
                query = "select obj from AccessCredential obj where obj.username = :username"),
})
@Entity
@Table(name = "accesscredential")
public class AccessCredential implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Expose
    @Column(unique = true, nullable = false, updatable = false)
    private String username;
    @Expose
    @Column
    private String password;
    @Expose
    @Column
    private String secretQuestion;

    @Expose
    @Column
    private String secretAnswer;

    @Expose
    @Column
    private boolean active = false;

    public AccessCredential() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}

