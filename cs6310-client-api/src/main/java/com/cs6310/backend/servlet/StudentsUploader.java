package com.cs6310.backend.servlet;

import com.cs6310.backend.cms.StudentManager;
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

public class StudentsUploader extends HttpServlet {

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


                    ArrayList<StudentsCSV> studentsCSVs = readCsvStream(br);


                    StudentManager studentManager = new StudentManager();

                    for (StudentsCSV studentsCSV : studentsCSVs) {
                        studentManager.addStudent(studentsCSV.studentId, studentsCSV.studentStatus, studentsCSV.maxCourses, studentsCSV.firstName, studentsCSV.lastName, "", studentsCSV.mobilePhone,
                                studentsCSV.email, studentsCSV.gender, studentsCSV.address, studentsCSV.username, studentsCSV.password, studentsCSV.secretQuestion,
                                studentsCSV.secretAnswer, studentsCSV.active);

                    }

                    for (StudentsCSV studentsCSV : studentsCSVs) {

                        if (studentsCSV.completedCourses.length() > 0 && !studentsCSV.completedCourses.equalsIgnoreCase("0")) {
                            if (studentsCSV.completedCourses.length() > 0 && studentsCSV.completedCourses.contains("-")) {

                                String[] completed = studentsCSV.completedCourses.split("-");

                                for (String name : completed) {
                                    studentManager.addStudentCompletedCourseCSV(studentsCSV.studentId, name);
                                }


                            } else if (studentsCSV.completedCourses.length() > 0) {
                                studentManager.addStudentCompletedCourseCSV(studentsCSV.studentId, studentsCSV.completedCourses);
                            }

                        }

                        if (studentsCSV.recommendedCourses.length() > 0 && !studentsCSV.recommendedCourses.equalsIgnoreCase("0")) {
                            if (studentsCSV.recommendedCourses.length() > 0 && studentsCSV.recommendedCourses.contains("-")) {

                                String[] recommended = studentsCSV.recommendedCourses.split("-");

                                for (String name : recommended) {
                                    studentManager.addStudentRecommendedCourseCSV(studentsCSV.studentId, name);
                                }


                            } else if (studentsCSV.recommendedCourses.length() > 0) {
                                studentManager.addStudentRecommendedCourseCSV(studentsCSV.studentId, studentsCSV.recommendedCourses);
                            }

                        }


                        if (studentsCSV.inProgressCourses.length() > 0 && !studentsCSV.inProgressCourses.equalsIgnoreCase("0")) {
                            if (studentsCSV.inProgressCourses.length() > 0 && studentsCSV.inProgressCourses.contains("-")) {

                                String[] recommended = studentsCSV.inProgressCourses.split("-");

                                for (String name : recommended) {
                                    studentManager.addStudentInProgressCourseCSV(studentsCSV.studentId, name);
                                }


                            } else if (studentsCSV.inProgressCourses.length() > 0) {
                                studentManager.addStudentInProgressCourseCSV(studentsCSV.studentId, studentsCSV.inProgressCourses);
                            }

                        }


                        if (studentsCSV.preffereCourses.length() > 0 && !studentsCSV.preffereCourses.equalsIgnoreCase("0")) {

                            if (studentsCSV.preffereCourses.length() > 0 && studentsCSV.preffereCourses.contains("-")) {

                                String[] recommended = studentsCSV.preffereCourses.split("-");

                                for (String name : recommended) {
                                    studentManager.addStudentPreferredCourseCSV(studentsCSV.studentId, name);
                                }


                            } else if (studentsCSV.preffereCourses.length() > 0) {
                                studentManager.addStudentPreferredCourseCSV(studentsCSV.studentId, studentsCSV.preffereCourses);
                            }

                        }


                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<StudentsCSV> readCsvStream(BufferedReader reader) throws IOException {

        JSONArray users = new JSONArray();
        String line;
        int count = 0;
        String[] headers = null;

        while ((line = reader.readLine()) != null) {

            if (count == 0) {
                headers = new String[line.split(",").length];
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

    private ArrayList<StudentsCSV> parseArray(JSONArray users) {
        ArrayList<StudentsCSV> userArrayList = new ArrayList<StudentsCSV>();

        for (Object object : users) {
            JSONObject user = (JSONObject) object;
            StudentsCSV data = new StudentsCSV();
            //


            if (user.containsKey("STUDENTID")) {
                data.studentId = (String) user.get("STUDENTID");
            }
            if (user.containsKey("STUDENTSTATUS")) {
                data.studentStatus = (String) user.get("STUDENTSTATUS");
            }

            if (user.containsKey("MAXCOURSES")) {
                data.maxCourses = (String) user.get("MAXCOURSES");
            }


            if (user.containsKey("FIRSTNAME")) {
                data.firstName = (String) user.get("FIRSTNAME");
            }

            if (user.containsKey("LASTNAME")) {
                data.lastName = (String) user.get("LASTNAME");
            }

            if (user.containsKey("MOBILEPHONE")) {
                data.mobilePhone = (String) user.get("MOBILEPHONE");
            }

            if (user.containsKey("EMAIL")) {
                data.email = (String) user.get("EMAIL");
            }

            if (user.containsKey("GENDER")) {
                data.gender = (String) user.get("GENDER");
            }

            if (user.containsKey("ADDRESS")) {
                data.address = (String) user.get("ADDRESS");
            }

            if (user.containsKey("USERNAME")) {
                data.username = (String) user.get("USERNAME");
            }

            if (user.containsKey("PASSWORD")) {
                data.password = (String) user.get("PASSWORD");
            }

            if (user.containsKey("SECRETQUESTION")) {
                data.secretQuestion = (String) user.get("SECRETQUESTION");
            }

            if (user.containsKey("SECRETANSWER")) {
                data.secretAnswer = (String) user.get("SECRETANSWER");
            }
            if (user.containsKey("ACTIVE")) {
                data.active = (String) user.get("ACTIVE");
            }


            if (user.containsKey("COMPLETEDCOURSES")) {
                data.completedCourses = (String) user.get("COMPLETEDCOURSES");
            }

            if (user.containsKey("RECOMMENDEDCOURSES")) {
                data.recommendedCourses = (String) user.get("RECOMMENDEDCOURSES");
            }
            if (user.containsKey("INPROGRESSCOURSES")) {
                data.inProgressCourses = (String) user.get("INPROGRESSCOURSES");
            }

            if (user.containsKey("PREFFEREDCOURSES")) {
                data.preffereCourses = (String) user.get("PREFFEREDCOURSES");
            }


            userArrayList.add(data);


        }

        return userArrayList;
    }


}
