package com.cs6310.backend.servlet;

import com.cs6310.backend.cms.CourseManager;
import com.cs6310.backend.cms.SemesterManager;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CourseSemesterUploader extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            List<FileItem> files = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
            for (FileItem file : files) {
                if (!file.isFormField()) {
                    InputStream stream = file.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));


                    ArrayList<CoursesSemestersCSV> coursesSemestersCSVs = readCsvStream(br);

                    SemesterManager semesterManager = new SemesterManager();


                    CourseManager courseManager = new CourseManager();

                    for (CoursesSemestersCSV coursesSemestersCSV : coursesSemestersCSVs) {

                        System.out.println(">>>>>>>>>>>>>" + coursesSemestersCSV.courseId);
                        System.out.println(">>>>>>>>>>>>>" + coursesSemestersCSV.courseName);


                        System.out.println(">>>>>>DDDDDDDD>>>>>>>" + coursesSemestersCSV.courseName);

                        courseManager.addCSVCourseData(coursesSemestersCSV.courseId, coursesSemestersCSV.hasPrerequisite, coursesSemestersCSV.mustBeOffered,
                                coursesSemestersCSV.courseName, coursesSemestersCSV.priority, coursesSemestersCSV.courseCredits, coursesSemestersCSV.maxEnrollment, coursesSemestersCSV.currentEnrollment);

                    }

                    for (CoursesSemestersCSV coursesSemestersCSV : coursesSemestersCSVs) {

                        String semesters = coursesSemestersCSV.semesterName;

                        if (semesters.length() > 0 && !semesters.equalsIgnoreCase("0")) {
                            if (semesters.length() > 0 && semesters.contains("-")) {
                                String[] data = semesters.split("-");
                                for (String name : data) {
                                    semesterManager.addSemester(name, coursesSemestersCSV.semesterYear, coursesSemestersCSV.semesterId);
                                }
                            } else if (semesters.length() > 0) {
                                semesterManager.addSemester(semesters, coursesSemestersCSV.semesterYear, coursesSemestersCSV.semesterId);
                            }
                        }
                    }


                    for (CoursesSemestersCSV coursesSemestersCSV : coursesSemestersCSVs) {

                        String semesters = coursesSemestersCSV.semesterName;
                        if (semesters.length() > 0 && !semesters.equalsIgnoreCase("0")) {
//                            if (semesters.length() > 0 && semesters.contains("-")) {
//                                String[] data = semesters.split("-");
//                                for (String name : data) {
//                                    courseManager.addCSVSemesterData(coursesSemestersCSV.courseId, name);
//                                }
//                            } else if (semesters.length() > 0) {

                            courseManager.addCSVSemesterData(coursesSemestersCSV.courseId.trim(), semesters.trim());
//                            }
                        }

                    }

//                    for (CoursesSemestersCSV coursesSemestersCSV : coursesSemestersCSVs) {
//                        if (!coursesSemestersCSV.prerequisite.equalsIgnoreCase("0")) {
//                            courseManager.addCSVPrerequisiteData(coursesSemestersCSV.courseId, coursesSemestersCSV.prerequisite);
//
//                        }
//                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("msg", "Error occurred when uploading schools :" + e.getMessage());
        }

    }

    public ArrayList<CoursesSemestersCSV> readCsvStream(BufferedReader reader) throws IOException {

        JSONArray users = new JSONArray();
        String line;
        int count = 0;
        String[] headers = null;

        while ((line = reader.readLine()) != null) {

            if (count == 0) {

                headers = line.split(",");

            } else {
                if (headers != null) {
                    JSONObject user = new JSONObject();


                    String[] array = line.split(",");
                    for (int i = 0; i < headers.length; i++) {

                        user.put(headers[i], array[i]);
                    }
                    users.add(user);

                }
            }

            count++;


        }

        return parseArray(users);


    }

    private ArrayList<CoursesSemestersCSV> parseArray(JSONArray users) {
        ArrayList<CoursesSemestersCSV> userArrayList = new ArrayList<CoursesSemestersCSV>();


        for (Object object : users) {
            JSONObject user = (JSONObject) object;
            CoursesSemestersCSV data = new CoursesSemestersCSV();

            if (user.containsKey("COURSEID")) {
                data.courseId = (String) user.get("COURSEID");
            }
            if (user.containsKey("HASPREREQUISITE")) {
                data.hasPrerequisite = (String) user.get("HASPREREQUISITE");
            }

            if (user.containsKey("MUSTBEOFFERED")) {
                data.mustBeOffered = (String) user.get("MUSTBEOFFERED");
            }

            if (user.containsKey("COURSENAME")) {
                data.courseName = (String) user.get("COURSENAME");
            }

            if (user.containsKey("PRIORITY")) {
                data.priority = (String) user.get("PRIORITY");
            }

            if (user.containsKey("COURSECREDITS")) {
                data.courseCredits = (String) user.get("COURSECREDITS");
            }

            if (user.containsKey("MAXENROLLMENT")) {
                data.maxEnrollment = (String) user.get("MAXENROLLMENT");
            }

            if (user.containsKey("CURRENTENROLLMENT")) {
                data.currentEnrollment = (String) user.get("CURRENTENROLLMENT");
            }

            if (user.containsKey("SEMESTERNAME")) {
                data.semesterName = (String) user.get("SEMESTERNAME");
            }

            if (user.containsKey("SEMESTERID")) {
                data.semesterId = (String) user.get("SEMESTERID");
            }

            if (user.containsKey("SEMESTERYEAR")) {
                data.semesterYear = (String) user.get("SEMESTERYEAR");
            }

            if (user.containsKey("PREREQUISITE")) {
                data.prerequisite = (String) user.get("PREREQUISITE");
            }

            userArrayList.add(data);


        }
        return userArrayList;
    }


}
