package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nelson on 10/21/15.
 */

@Cacheable
@NamedQueries({
        @NamedQuery(name = "Permit.findByApplicantNames",
                query = "select obj from Permit obj where obj.personDetails.firstName =: firstName OR obj.personDetails.lastName =: lastName "),
        @NamedQuery(name = "Permit.getAll", query = "select obj from Permit obj"),
        @NamedQuery(name = "Permit.getById", query = "select obj from Permit obj where obj.id = :id"),
        @NamedQuery(name = "Permit.getByNumber", query = "select obj from Permit obj where obj.permitNumber = :id")
})
@Entity
public class Permit implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;
    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;
    @Expose
    @Column
    private String latitude;
    @Expose
    @Column
    private String longitude;
    @Expose
    @Column
    private String status;
    @Expose
    @Column
    private int waterVolume;

    @Expose
    @Column
    private int permitNumber;

    public Permit() {
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

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(int waterVolume) {
        this.waterVolume = waterVolume;
    }


}
