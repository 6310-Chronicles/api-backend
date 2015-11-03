package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/3/15.
 */
@Entity
public class Professor  implements Serializable {


    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;


    @Expose
    private boolean available;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;


    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AccessCredential accessCredential;


    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "competent")
    private List<Course> competentCourseList;


    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "offering")
    private List<Course> teachingCourseList;


    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }





    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AccessCredential getAccessCredential() {
        return accessCredential;
    }

    public void setAccessCredential(AccessCredential accessCredential) {
        this.accessCredential = accessCredential;
    }


    public List<Course> getTeachingCourseList() {
        if (teachingCourseList==null)
            teachingCourseList= new ArrayList<>();


            return teachingCourseList;
    }

    public void setTeachingCourseList(List<Course> teachingCourseList) {
        this.teachingCourseList = teachingCourseList;
    }

    public List<Course> getCompetentCourseList() {
        if (competentCourseList==null)
            competentCourseList= new ArrayList<>();

        return competentCourseList;
    }

    public void setCompetentCourseList(List<Course> competentCourseList) {
        this.competentCourseList = competentCourseList;
    }
}
