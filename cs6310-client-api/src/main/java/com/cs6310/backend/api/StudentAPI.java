package com.cs6310.backend.api;

import com.cs6310.backend.cms.StudentManager;
import com.cs6310.backend.model.Student;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/student")
public class StudentAPI {


    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(payload);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/update")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(payload);


        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/addStudentsCompletedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addStudentsCompletedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/removeStudentsCompletedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeStudentsCompletedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/addStudentsRecommendedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addStudentsRecommendedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/removeStudentsRecommendedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeStudentsRecommendedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/addStudentsProgressCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addStudentsProgressCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/removeStudentsProgressCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeStudentsProgressCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/addStudentsToBeOptimizedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addStudentsToBeOptimizedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @POST
    @Path("/removeStudentsToBeOptimizedCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeStudentsToBeOptimizedCourse(@FormParam("studentUUID") String studentUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @GET
    @Path("/allStudents")
    public Response getAllStudents() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

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
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @GET
    @Path("/student/{uuid}")
    public Response getStudent(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

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
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @POST
    @Path("/deleteStudentByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response deleteStudentByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


}
