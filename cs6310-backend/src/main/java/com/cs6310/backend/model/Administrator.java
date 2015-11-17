package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nelson on 10/14/15.
 */

@Cacheable
@NamedQueries({
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.findByCredentials",
                query = "select obj from Administrator obj where obj.accessCredential = : accessCredential"),
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.getAll", query = "select obj from Administrator obj"),
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.getById", query = "select obj from Administrator obj where obj.id = :id"),
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.getByUUID",
                query = "select obj from Administrator obj where obj.uuid =: uuid"),
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.getByPersonalDetails",
                query = "select obj from Administrator obj where obj.personDetails =: personalDetails"),
        @NamedQuery(name = "com.cs6310.backend.model.Administrator.getByAdministratorId",
                query = "select obj from Administrator obj where obj.administratorId =: administratorId")

})

@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column
    private String uuid;

    @Expose
    @Column(unique = true, nullable = false)
    private String administratorId;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;


    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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
