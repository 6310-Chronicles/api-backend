package com.cs6310.backend.api;

import com.cs6310.backend.cms.StudentManager;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.model.Student;
import com.cs6310.backend.request.StudenCourses;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.cs6310.backend.response.StudentsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/student")
public class StudentAPI {


    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(
            @FormParam("studentId") String studentId,
            @FormParam("maxCourses") String maxCourses,
            @FormParam("studentStatus") String studentStatus,
            @FormParam("lastName") String lastName,
            @FormParam("firstName") String firstName,
            @FormParam("mobilePhone") String mobilePhone,
            @FormParam("email") String email,
            @FormParam("gender") String gender,
            @FormParam("address") String address,
            @FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("secretQuestion") String secretQuestion,
            @FormParam("secretAnswer") String secretAnswer,
            @FormParam("active") String active) {


        StudentManager studentManager = new StudentManager();
        String error = studentManager.addStudent(studentId, studentStatus, maxCourses, firstName, lastName, "",
                mobilePhone, email, gender, address, userName, password, secretQuestion,
                secretAnswer, active);

        APIResponse payload = new APIResponse();
        if (error != null) {
            payload.setStatus(ResponseStatus.OK);
        } else {
            payload.setStatus(ResponseStatus.FAILED);
            payload.setErrorCause(error);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/update")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(
            @FormParam("uuid") String uuid,
            @FormParam("studentId") String studentId,
            @FormParam("maxCourses") String maxCourses,
            @FormParam("studentStatus") String studentStatus,
            @FormParam("lastName") String lastName,
            @FormParam("firstName") String firstName,
            @FormParam("mobilePhone") String mobilePhone,
            @FormParam("email") String email,
            @FormParam("gender") String gender,
            @FormParam("address") String address,
            @FormParam("userName") String userName,
            @FormParam("password") String password,
            @FormParam("secretQuestion") String secretQuestion,
            @FormParam("secretAnswer") String secretAnswer,
            @FormParam("active") String active) {


        StudentManager studentManager = new StudentManager();

        String error = studentManager.updateStudent(uuid, studentId, studentStatus, maxCourses, firstName, lastName, "",
                mobilePhone, email, gender, address, userName, password, secretQuestion,
                secretAnswer, active);

        APIResponse payload = new APIResponse();
        if (error != null) {
            payload.setStatus(ResponseStatus.OK);
        } else {
            payload.setStatus(ResponseStatus.FAILED);
            payload.setErrorCause(error);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/addStudentsCompletedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudentsCompletedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.addStudentsCompletedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/removeStudentsCompletedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeStudentsCompletedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.removeStudentsCompletedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/addStudentsRecommendedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudentsRecommendedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.addStudentsRecommendedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/removeStudentsRecommendedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeStudentsRecommendedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.removeStudentsRecommendedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/addStudentsProgressCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudentsProgressCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.addStudentsProgressCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/removeStudentsProgressCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeStudentsProgressCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.removeStudentsProgressCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/addStudentsToBeOptimizedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudentsToBeOptimizedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.addStudentsToBeOptimizedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/removeStudentsToBeOptimizedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeStudentsToBeOptimizedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();

            String response = studentManager.removeStudentsToBeOptimizedCourse(studentUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @GET
    @Path("/allStudents")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStudents() {
        APIResponse payload = new APIResponse();

        StudentManager studentManager = new StudentManager();


        try {
            List<Student> list = studentManager.getAllStudents();

            if (list != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(list);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + list + " does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @GET
    @Path("/student/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();

        StudentManager studentManager = new StudentManager();

        try {
            Student student = studentManager.getStudentByUUID(uuid);

            if (student != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(student);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + student + " does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/deleteStudentByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteStudentByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        try {
            StudentManager studentManager = new StudentManager();
            String response = studentManager.deleteStudentByUUID(uuid);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/studentsData")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudentsPreference(
            @FormParam("studentsData") String data) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        StudenCourses studenCourses = gson.fromJson(data, StudenCourses.class);

        studenCourses.message = "Please wait for response";


        StudentManager studentManager = new StudentManager();

        List<String> list = studenCourses.getCourses();
        int size = list.size();
        for (int i = 0; i < size; i++) {

            String course = list.get(i);

            studentManager.addStudentPreferredCourseCSV(studenCourses.getStudentId(), course.trim());

        }

        Student student = studentManager.getStudentByUUID(studenCourses.getStudentId());


        List<Course> courseList = student.getRecommendedCourses();
        if (courseList != null && courseList.size() > 0) {
            studenCourses.status = true;
            for (Course course : student.getRecommendedCourses()) {
                studenCourses.getCourses().add(course.getCourseId());

            }

        } else {

            studenCourses.status = false;
            studenCourses.courses = null;
        }


        APIResponse payload = new APIResponse();
        payload.setStatus(ResponseStatus.OK);


        String json = gson.toJson(studenCourses);


        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @POST
    @Path("/studentsDataJson")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createStudentsData(
            @FormParam("studentsData") String data) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        StudentsResponse studenCourses = gson.fromJson(data, StudentsResponse.class);

        StudentManager studentManager = new StudentManager();

        for (Student student : studenCourses.response) {

            for (Course course : student.getRecommendedCourses()) {

                studentManager.addStudentRecommendedCourseCSV(student.getStudentId(), course.getCourseId());
            }

            break;
        }


        APIResponse payload = new APIResponse();
        payload.setStatus(ResponseStatus.OK);

        String json = gson.toJson(payload);


        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }



}
