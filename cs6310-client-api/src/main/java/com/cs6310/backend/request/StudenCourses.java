package com.cs6310.backend.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/25/15.
 */
public class StudenCourses {

    public String studentId;
    public String message;
    public List<String> courses;
    public boolean status;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<String> getCourses() {

        if (courses == null)
            courses = new ArrayList<String>();
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
