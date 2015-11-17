package com.cs6310.backend.servlet;

import com.cs6310.backend.cms.TeachingAssistantManager;
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

public class TeachingAssistantUploader extends HttpServlet {

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


                    ArrayList<TeachingAssistantsCSV> teachingAssistantsCSVs = readCsvStream(br);


                    TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();


                    for (TeachingAssistantsCSV teachingAssistantsCSV : teachingAssistantsCSVs) {

                        teachingAssistantManager.addTeachingAssistantCSV(teachingAssistantsCSV.studentId);

                    }

                    for (TeachingAssistantsCSV teachingAssistantsCSV : teachingAssistantsCSVs) {

                        if (teachingAssistantsCSV.assistingCourses.length() > 0 && !teachingAssistantsCSV.assistingCourses.equalsIgnoreCase("0")) {
                            if (teachingAssistantsCSV.assistingCourses.length() > 0 && teachingAssistantsCSV.assistingCourses.contains("-")) {

                                String[] completed = teachingAssistantsCSV.assistingCourses.split("-");

                                for (String name : completed) {
                                    teachingAssistantManager.addTAAssitingCourseCSV(teachingAssistantsCSV.studentId, name);
                                }


                            } else if (teachingAssistantsCSV.assistingCourses.length() > 0) {
                                teachingAssistantManager.addTAAssitingCourseCSV(teachingAssistantsCSV.studentId, teachingAssistantsCSV.assistingCourses);
                            }

                        }


                    }

                    for (TeachingAssistantsCSV teachingAssistantsCSV : teachingAssistantsCSVs) {

                        if (teachingAssistantsCSV.competentCourses.length() > 0 && !teachingAssistantsCSV.competentCourses.equalsIgnoreCase("0")) {
                            if (teachingAssistantsCSV.competentCourses.length() > 0 && teachingAssistantsCSV.competentCourses.contains("-")) {

                                String[] completed = teachingAssistantsCSV.competentCourses.split("-");

                                for (String name : completed) {
                                    teachingAssistantManager.addTACompetencyCourseCSV(teachingAssistantsCSV.studentId, name);
                                }


                            } else if (teachingAssistantsCSV.competentCourses.length() > 0) {
                                teachingAssistantManager.addTACompetencyCourseCSV(teachingAssistantsCSV.studentId, teachingAssistantsCSV.competentCourses);
                            }

                        }


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<TeachingAssistantsCSV> readCsvStream(BufferedReader reader) throws IOException {

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

    private ArrayList<TeachingAssistantsCSV> parseArray(JSONArray users) {
        ArrayList<TeachingAssistantsCSV> userArrayList = new ArrayList<TeachingAssistantsCSV>();

        for (Object object : users) {
            JSONObject user = (JSONObject) object;
            TeachingAssistantsCSV data = new TeachingAssistantsCSV();


            if (user.containsKey("STUDENTID")) {
                data.studentId = (String) user.get("STUDENTID");
            }
            if (user.containsKey("ASSISTINGCOURSES")) {
                data.assistingCourses = (String) user.get("ASSISTINGCOURSES");
            }

            if (user.containsKey("COMPETENTCOURSES")) {
                data.competentCourses = (String) user.get("COMPETENTCOURSES");
            }


            userArrayList.add(data);


        }

        return userArrayList;
    }


}
