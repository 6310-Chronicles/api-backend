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
        @NamedQuery(name = "com.cs6310.backend.model.Course.getAll", query = "select obj from Course obj"),
        @NamedQuery(name = "com.cs6310.backend.model.Course.getByUUID", query = "select obj from Course obj where obj.uuid = :uuid"),
        @NamedQuery(name = "com.cs6310.backend.model.Course.getByName", query = "select obj from Course obj where obj.name = :name")
})
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer priority;

    @Expose
    private Integer courseCredits;

    @Expose
    private Integer maximumEnrollment;

    @Expose
    private Integer currentEnrollment;

    @Expose
    private List<String> listOfPrerequisiteCourses;

    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Semester.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Semester> offeredInSemester = new ArrayList<>();


    @ManyToMany(targetEntity = com.cs6310.backend.model.Professor.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "competentCourseList")
    private List<Professor> professorCompetency = new ArrayList<>();

    @ManyToMany(targetEntity = com.cs6310.backend.model.Professor.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "teachingCourseList")
    private List<Professor> professorsTeaching = new ArrayList<>();


    @ManyToMany(targetEntity = com.cs6310.backend.model.Student.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "completedCourses")
    private List<Student> studentCompleted = new ArrayList<>();

    @ManyToMany(targetEntity = com.cs6310.backend.model.Student.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "recommendedCourses")
    private List<Student> studentRecommended = new ArrayList<>();


    @ManyToMany(targetEntity = com.cs6310.backend.model.Student.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "coursesInProgress")
    private List<Student> studentProgress = new ArrayList<>();

    @ManyToMany(targetEntity = com.cs6310.backend.model.Student.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "preferedCoursesToBeOptimized")
    private List<Student> studentPreferred = new ArrayList<>();

    @ManyToMany(targetEntity = com.cs6310.backend.model.TeachingAssistant.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "competency")
    private List<TeachingAssistant> teachingAssistantCompetency = new ArrayList<>();

    @ManyToMany(targetEntity = com.cs6310.backend.model.TeachingAssistant.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "assisting")
    private List<TeachingAssistant> teachingAssistantAssisting = new ArrayList<>();


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

    public List<String> getListOfPrerequisiteCourses() {
        if (listOfPrerequisiteCourses == null)
            listOfPrerequisiteCourses = new ArrayList<>();
        return listOfPrerequisiteCourses;
    }

    public void setListOfPrerequisiteCourses(List<String> listOfPrerequisiteCourses) {
        this.listOfPrerequisiteCourses = listOfPrerequisiteCourses;
    }

    public List<Semester> getOfferedInSemester() {
        if (offeredInSemester == null)
            offeredInSemester = new ArrayList<>();
        return offeredInSemester;
    }

    public void setOfferedInSemester(List<Semester> offeredInSemester) {
        this.offeredInSemester = offeredInSemester;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
