package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 10/14/15.
 */

@Cacheable
@NamedQueries({
        @NamedQuery(name = "com.cs6310.backend.model.Student.findByCredentials", query = "select obj from Student obj where obj.accessCredential = : accessCredential"),
        @NamedQuery(name = "com.cs6310.backend.model.Student.getAll", query = "select obj from Student obj"),
        @NamedQuery(name = "com.cs6310.backend.model.Student.getByStudentId", query = "select obj from Student obj where obj.studentId = :studentId"),
        @NamedQuery(name = "com.cs6310.backend.model.Student.getByUUID", query = "select obj from Student obj where obj.uuid =: uuid"),
        @NamedQuery(name = "com.cs6310.backend.model.Student.getByPersonalDetails", query = "select obj from Student obj where obj.personDetails =: personalDetails")

})

@Entity
@Table(name = "student")
public class Student implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column
    private String uuid;

    @Expose
    @Column
    private String studentId;

    @Expose
    @Column
    private String studentStatus;

    @Expose
    @Column
    private Integer maxCourses;


    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private AccessCredential accessCredential;



    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> completedCourses = new ArrayList<>();


    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> recommendedCourses = new ArrayList<>();


    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> coursesInProgress = new ArrayList<>();

    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> preferedCoursesToBeOptimized = new ArrayList<>();


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


    public AccessCredential getAccessCredential() {
        return accessCredential;
    }

    public void setAccessCredential(AccessCredential accessCredential) {
        this.accessCredential = accessCredential;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Integer getMaxCourses() {
        return maxCourses;
    }

    public void setMaxCourses(Integer maxCourses) {
        this.maxCourses = maxCourses;
    }

    public List<Course> getCompletedCourses() {
        if (completedCourses==null)
            completedCourses = new ArrayList<>();
        return completedCourses;
    }

    public void setCompletedCourses(List<Course> completedCourses) {


        this.completedCourses = completedCourses;
    }

    public List<Course> getRecommendedCourses() {

        if (recommendedCourses==null)
            recommendedCourses =  new ArrayList<>();
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<Course> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

    public List<Course> getCoursesInProgress() {
        if (coursesInProgress==null)
            coursesInProgress =  new ArrayList<>();
        return coursesInProgress;
    }

    public void setCoursesInProgress(List<Course> coursesInProgress) {
        this.coursesInProgress = coursesInProgress;
    }

    public List<Course> getPreferedCoursesToBeOptimized() {
        if (preferedCoursesToBeOptimized == null)
            preferedCoursesToBeOptimized = new ArrayList<>();
        return preferedCoursesToBeOptimized;
    }

    public void setPreferedCoursesToBeOptimized(List<Course> preferedCoursesToBeOptimized) {
        this.preferedCoursesToBeOptimized = preferedCoursesToBeOptimized;
    }
}
