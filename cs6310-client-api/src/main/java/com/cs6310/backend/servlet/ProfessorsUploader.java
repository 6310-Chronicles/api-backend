package com.cs6310.backend.servlet;

import com.cs6310.backend.cms.ProfessorManager;
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

public class ProfessorsUploader extends HttpServlet {

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


                    ArrayList<ProfessorsCSV> professorsCSVs = readCsvStream(br);


                    ProfessorManager professorManager = new ProfessorManager();

                    for (ProfessorsCSV professorsCSV : professorsCSVs) {
                        professorManager.addProf(professorsCSV.profId, professorsCSV.available, professorsCSV.firstName, professorsCSV.lastName, "", professorsCSV.mobilePhone,
                                professorsCSV.email, professorsCSV.gender, professorsCSV.address, professorsCSV.username, professorsCSV.password, professorsCSV.secretQuestion,
                                professorsCSV.secretAnswer, professorsCSV.active);

                    }

                    for (ProfessorsCSV professorsCSV : professorsCSVs) {

                        if (professorsCSV.competentCourses.length() > 0 && !professorsCSV.competentCourses.equalsIgnoreCase("0")) {
                            if (professorsCSV.competentCourses.length() > 0 && professorsCSV.competentCourses.contains("-")) {

                                String[] competent = professorsCSV.competentCourses.split("-");

                                for (String name : competent) {
                                    professorManager.addProfCompetentCourseCSV(professorsCSV.profId, name);
                                }

                            } else if (professorsCSV.competentCourses.length() > 0) {
                                professorManager.addProfCompetentCourseCSV(professorsCSV.profId, professorsCSV.competentCourses);
                            }

                        }
                        if (professorsCSV.teachingCourses.length() > 0 && !professorsCSV.teachingCourses.equalsIgnoreCase("0")) {

                            if (professorsCSV.teachingCourses.length() > 0 && professorsCSV.teachingCourses.contains("-")) {

                                String[] teaching = professorsCSV.teachingCourses.split("-");

                                for (String name : teaching) {
                                    professorManager.addProfTeachingCourseCSV(professorsCSV.profId, name);
                                }
                            } else if (professorsCSV.teachingCourses.length() > 0) {
                                professorManager.addProfTeachingCourseCSV(professorsCSV.profId, professorsCSV.teachingCourses);
                            }
                        }

                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("msg", "Error occurred when uploading schools :" + e.getMessage());
        }

    }

    public ArrayList<ProfessorsCSV> readCsvStream(BufferedReader reader) throws IOException {

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

    private ArrayList<ProfessorsCSV> parseArray(JSONArray users) {
        ArrayList<ProfessorsCSV> userArrayList = new ArrayList<ProfessorsCSV>();

        for (Object object : users) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            JSONObject user = (JSONObject) object;
            ProfessorsCSV adminstratorCSV = new ProfessorsCSV();


            if (user.containsKey("PROFID")) {
                adminstratorCSV.profId = (String) user.get("PROFID");
            }
            if (user.containsKey("AVAILABLE")) {
                adminstratorCSV.available = (String) user.get("AVAILABLE");
            }

            if (user.containsKey("FIRSTNAME")) {
                adminstratorCSV.firstName = (String) user.get("FIRSTNAME");
            }

            if (user.containsKey("LASTNAME")) {
                adminstratorCSV.lastName = (String) user.get("LASTNAME");
                System.out.println("LASTNAME:------------" + user.get("LASTNAME"));
            }

            if (user.containsKey("MOBILEPHONE")) {
                adminstratorCSV.mobilePhone = (String) user.get("MOBILEPHONE");
            }

            if (user.containsKey("EMAIL")) {
                adminstratorCSV.email = (String) user.get("EMAIL");
                System.out.println("EMAIL:------------" + user.get("EMAIL"));
            }

            if (user.containsKey("GENDER")) {
                adminstratorCSV.gender = (String) user.get("GENDER");
            }

            if (user.containsKey("ADDRESS")) {
                adminstratorCSV.address = (String) user.get("ADDRESS");
            }

            if (user.containsKey("USERNAME")) {
                adminstratorCSV.username = (String) user.get("USERNAME");
            }

            if (user.containsKey("PASSWORD")) {
                adminstratorCSV.password = (String) user.get("PASSWORD");
            }

            if (user.containsKey("SECRETQUESTION")) {
                adminstratorCSV.secretQuestion = (String) user.get("SECRETQUESTION");
            }

            if (user.containsKey("SECRETANSWER")) {
                adminstratorCSV.secretAnswer = (String) user.get("SECRETANSWER");
            }
            if (user.containsKey("ACTIVE")) {
                adminstratorCSV.active = (String) user.get("ACTIVE");
            }


            if (user.containsKey("COMPETENTCOURSES")) {
                adminstratorCSV.competentCourses = (String) user.get("COMPETENTCOURSES");
            }

            if (user.containsKey("TEACHINGCOURSES")) {
                adminstratorCSV.teachingCourses = (String) user.get("TEACHINGCOURSES");
            }


            userArrayList.add(adminstratorCSV);


        }

        return userArrayList;
    }


}
