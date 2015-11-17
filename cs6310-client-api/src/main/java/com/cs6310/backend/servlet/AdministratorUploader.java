package com.cs6310.backend.servlet;

import com.cs6310.backend.cms.AdministratorManager;
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

public class AdministratorUploader extends HttpServlet {

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


                    ArrayList<AdminstratorCSV> adminstratorCSVs = readCsvStream(br);

                    System.out.println("Number of schools:------------" + adminstratorCSVs.size());

                    AdministratorManager administratorManager = new AdministratorManager();

                    for (AdminstratorCSV adminstratorCSV : adminstratorCSVs) {
                        administratorManager.addAdministrator(adminstratorCSV.adminstratorId, adminstratorCSV.firstName, adminstratorCSV.lastName, "", adminstratorCSV.mobilePhone,
                                adminstratorCSV.email, adminstratorCSV.gender, adminstratorCSV.address, adminstratorCSV.username, adminstratorCSV.password, adminstratorCSV.secretQuestion,
                                adminstratorCSV.secretAnswer, adminstratorCSV.active);

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("msg", "Error occurred when uploading schools :" + e.getMessage());
        }

    }

    public ArrayList<AdminstratorCSV> readCsvStream(BufferedReader reader) throws IOException {

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

        return parseAdminstratorsArray(users);


    }

    private ArrayList<AdminstratorCSV> parseAdminstratorsArray(JSONArray users) {
        ArrayList<AdminstratorCSV> userArrayList = new ArrayList<AdminstratorCSV>();

        for (Object object : users) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            JSONObject user = (JSONObject) object;
            AdminstratorCSV adminstratorCSV = new AdminstratorCSV();

            if (user.containsKey("ADMINID")) {
                adminstratorCSV.adminstratorId = (String) user.get("ADMINID");

                System.out.println("ADMINID:------------" + user.get("ADMINID"));
            }
            if (user.containsKey("FIRSTNAME")) {
                adminstratorCSV.firstName = (String) user.get("FIRSTNAME");
                System.out.println("FIRSTNAME:------------" + user.get("FIRSTNAME"));
            }
//				personDetails.setLast_name((String) user.get("LASTNAME"));
            if (user.containsKey("LASTNAME")) {
                adminstratorCSV.lastName = (String) user.get("LASTNAME");
                System.out.println("LASTNAME:------------" + user.get("LASTNAME"));
            }

            if (user.containsKey("MOBILEPHONE")) {
                adminstratorCSV.mobilePhone = (String) user.get("MOBILEPHONE");
                System.out.println("MOBILEPHONE:------------" + user.get("MOBILEPHONE"));
            }

            if (user.containsKey("EMAIL")) {
                adminstratorCSV.email = (String) user.get("EMAIL");
                System.out.println("EMAIL:------------" + user.get("EMAIL"));
            }

            if (user.containsKey("GENDER")) {
                adminstratorCSV.gender = (String) user.get("GENDER");
                System.out.println("GENDER:------------" + user.get("GENDER"));
            }

            if (user.containsKey("ADDRESS")) {
                adminstratorCSV.address = (String) user.get("ADDRESS");
                System.out.println("ADDRESS:------------" + user.get("ADDRESS"));
            }

            if (user.containsKey("USERNAME")) {
                adminstratorCSV.username = (String) user.get("USERNAME");
                System.out.println("USERNAME:------------" + user.get("USERNAME"));
            }

            if (user.containsKey("PASSWORD")) {
                adminstratorCSV.password = (String) user.get("PASSWORD");
                System.out.println("PASSWORD:------------" + user.get("PASSWORD"));
            }

            if (user.containsKey("SECRETQUESTION")) {
                adminstratorCSV.secretQuestion = (String) user.get("SECRETQUESTION");
                System.out.println("SECRETQUESTION:------------" + user.get("SECRETQUESTION"));
            }

            if (user.containsKey("SECRETANSWER")) {
                adminstratorCSV.secretAnswer = (String) user.get("SECRETANSWER");
                System.out.println("SECRETANSWER:------------" + user.get("SECRETANSWER"));
            }

            if (user.containsKey("ACTIVE")) {
                adminstratorCSV.active = (String) user.get("ACTIVE");
                System.out.println("ACTIVE:------------" + user.get("ACTIVE"));
            }


            userArrayList.add(adminstratorCSV);


        }

        return userArrayList;
    }


}
