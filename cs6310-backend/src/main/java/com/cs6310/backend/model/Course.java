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
        @NamedQuery(name = "com.cs6310.backend.model.Course.getByName", query = "select obj from Course obj where obj.name = :name"),
        @NamedQuery(name = "com.cs6310.backend.model.Course.getCourseID", query = "select obj from Course obj where obj.courseId = :courseId")
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
    @Column(unique = true, nullable = false)
    private String courseId;

    @Expose
    private boolean hasPrerequisite;

    @Expose
    private boolean mustBeOffered;

    @Expose
    private String courseName;

    @Expose
    private int priority;

    @Expose
    private Integer courseCredits;

    @Expose
    private int maximumEnrollment;

    @Expose
    private Integer currentEnrollment;
    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "COURSE_PREREQUISITE",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id", referencedColumnName = "id"))
    private List<Course> prerequisites = new ArrayList();
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "prerequisites")
    private List<Course> prerequisitesData = new ArrayList();


//    @ManyToMany
//    @JoinTable(name="USER_FAN",
//            joinColumns= @JoinColumn(name="user_id", referencedColumnName="id"),
//            inverseJoinColumns=  @JoinColumn(name="fan_id",referencedColumnName="id"))
//    private List myFans = new ArrayList();
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

    public Course() {
    }


    // Added by DUY
    public Course(String courseId) {
        this.courseId = courseId;
    }

    // Added by DUY
    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    // Added by DUY
    public Course(String courseId, String courseName, int priority) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.priority = priority;
    }

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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
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

    public List<Course> getListOfPrerequisiteCourses() {
        return prerequisites;
    }

    public void setListOfPrerequisiteCourses(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }


}
