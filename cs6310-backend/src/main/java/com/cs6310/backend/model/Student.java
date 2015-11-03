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
        @NamedQuery(name = "Student.findByCredentials", query = "select obj from Student obj where obj.accessCredential = : accessCredential"),
        @NamedQuery(name = "Student.getAll", query = "select obj from Student obj"),
        @NamedQuery(name = "Student.getByStudentId", query = "select obj from Student obj where obj.studentId = :studentId"),
        @NamedQuery(name = "Student.getByUUID",query = "select obj from Student obj where obj.uuid =: uuid"),
        @NamedQuery(name = "Student.getByPersonalDetails", query = "select obj from Student obj where obj.personDetails =: personalDetails")

})

@Entity
public class Student implements Serializable {

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
    private String studentId;

    @Expose
    @Column
    private String studentStatus;

    @Expose
    @Column
    private Integer maxCourses;

    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "studentCompleted")
    private List<Course> completedCourses;

    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "studentRecommendation")
    private List<Course> recommendedCourses;


    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "studentProgress")
    private List<Course> coursesInProgress;


    @Expose
    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "studentsOptimized")
    private List<Course> prefferedCoursesToBeOptimized;



    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonDetails personDetails;

    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AccessCredential accessCredential;

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

    public List<Course> getPrefferedCoursesToBeOptimized() {
        if (prefferedCoursesToBeOptimized==null)
            prefferedCoursesToBeOptimized =  new ArrayList<>();
        return prefferedCoursesToBeOptimized;
    }

    public void setPrefferedCoursesToBeOptimized(List<Course> prefferedCoursesToBeOptimized) {
        this.prefferedCoursesToBeOptimized = prefferedCoursesToBeOptimized;
    }
}
