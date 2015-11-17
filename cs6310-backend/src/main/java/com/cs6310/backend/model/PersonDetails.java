package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nelson on 10/14/15.
 */

@Cacheable
@NamedQueries({

})
@Entity
public class PersonDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;
    @Expose
    @Column
    private String firstName;
    @Expose
    @Column
    private String lastName;
    @Expose
    @Column(length = 1000)
    private String profilePic;
    @Expose
    @Column
    private String mobilePhone;
    @Expose
    @Column
    private String email;
    @Expose
    @Column
    private String gender;

    @Expose
    @Column
    private String address;


    public PersonDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String itemId) {
        this.uuid = itemId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}