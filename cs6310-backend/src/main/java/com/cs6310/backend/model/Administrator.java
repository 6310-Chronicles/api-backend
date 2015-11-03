package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nelson on 10/14/15.
 */

@Cacheable
@NamedQueries({
        @NamedQuery(name = "Administrator.findByCredentials",
                query = "select obj from Administrator obj where obj.accessCredential = : accessCredential"),
        @NamedQuery(name = "Administrator.getAll", query = "select obj from Administrator obj"),
        @NamedQuery(name = "Administrator.getById", query = "select obj from Administrator obj where obj.id = :id"),
        @NamedQuery(name = "Administrator.getByUUID",
                query = "select obj from Administrator obj where obj.uuid =: uuid"),
        @NamedQuery(name = "Administrator.getByPersonalDetails",
                query = "select obj from Administrator obj where obj.personDetails =: personalDetails")

})

@Entity
public class Administrator implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column
    private String uuid;

    @Expose
    @Column
    private String administratorId;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;

    @Expose
    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "administrator")
    private List<Role> roles;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AccessCredential accessCredential;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public List<Role> getRoles() {
        Set<Role> roleSet = new HashSet<Role>(roles);
        return new ArrayList<Role>(roleSet);
    }

    public void setRoles(List<Role> roles) {
        Set<Role> roleSet = new HashSet<Role>(roles);
        this.roles = new ArrayList<Role>(roleSet);
    }

    public AccessCredential getAccessCredential() {
        return accessCredential;
    }

    public void setAccessCredential(AccessCredential accessCredential) {
        this.accessCredential = accessCredential;
    }


    public String getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(String administratorId) {
        this.administratorId = administratorId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
