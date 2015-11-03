package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/3/15.
 */

@Cacheable
@NamedQueries({
        @NamedQuery(name = "Course.getAll", query = "select obj from Course obj"),
        @NamedQuery(name = "Course.getByUUID", query = "select obj from Course obj where obj.uuid = :uuid"),
        @NamedQuery(name = "Course.getByName", query = "select obj from Course obj where obj.name = :name")
})
@Entity
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;


    @Expose
    private String courseId;

    @Expose
    private boolean hasPrerequisite;

    @Expose
    private boolean mustBeOffered;

    @Expose
    private Integer courseName;


    @Expose
    private Integer courseCredits;

    @Expose
    private Integer maximumEnrollment;

    @Expose
    private Integer currentEnrollment;

    @Expose
    private List<Course> listOfPrerequisiteCourses;

    @Expose
    @ManyToMany(targetEntity = Semester.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "course")
    private List<Semester> offeredInSemester;


    @ManyToMany(targetEntity = Student.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentCompleted;

    @ManyToMany(targetEntity = Student.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentRecommendation;


    @ManyToMany(targetEntity = Student.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentProgress;

    @ManyToMany(targetEntity = Student.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Student> studentsOptimized;


    @ManyToMany(targetEntity = Professor.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Professor> competent;



    @ManyToMany(targetEntity = Professor.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Professor> offering;



    @ManyToMany(targetEntity = Professor.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Professor> competency;



    @ManyToMany(targetEntity = Professor.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Professor> assisting;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean isHasPrerequisite() {
        return hasPrerequisite;
    }

    public void setHasPrerequisite(boolean hasPrerequisite) {
        this.hasPrerequisite = hasPrerequisite;
    }

    public boolean isMustBeOffered() {
        return mustBeOffered;
    }

    public void setMustBeOffered(boolean mustBeOffered) {
        this.mustBeOffered = mustBeOffered;
    }

    public Integer getCourseName() {
        return courseName;
    }

    public void setCourseName(Integer courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(Integer courseCredits) {
        this.courseCredits = courseCredits;
    }

    public Integer getMaximumEnrollment() {
        return maximumEnrollment;
    }

    public void setMaximumEnrollment(Integer maximumEnrollment) {
        this.maximumEnrollment = maximumEnrollment;
    }

    public Integer getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(Integer currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

    public List<Course> getListOfPrerequisiteCourses() {
        if (listOfPrerequisiteCourses==null)
            listOfPrerequisiteCourses= new ArrayList<>();
        return listOfPrerequisiteCourses;
    }

    public void setListOfPrerequisiteCourses(List<Course> listOfPrerequisiteCourses) {
        this.listOfPrerequisiteCourses = listOfPrerequisiteCourses;
    }

    public List<Semester> getOfferedInSemester() {
        if (offeredInSemester==null)
            offeredInSemester= new ArrayList<>();
        return offeredInSemester;
    }

    public void setOfferedInSemester(List<Semester> offeredInSemester) {
        this.offeredInSemester = offeredInSemester;
    }
}
