package com.cs6310.backend.api;

import com.cs6310.backend.cms.CourseManager;
import com.cs6310.backend.model.Course;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by nelson on 11/6/15.
 */
@Path("/course")
public class CourseAPI {

    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response create(
            @FormParam("courseId") String courseId,
            @FormParam("hasPrerequisite") String hasPrerequisite,
            @FormParam("mustBeOffered") String mustBeOffered,
            @FormParam("courseName") String courseName,
            @FormParam("courseCredits") String courseCredits,
            @FormParam("priority") String priority,
            @FormParam("maximumEnrollment") String maximumEnrollment,
            @FormParam("currentEnrollment") String currentEnrollment) {

        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.addCourse(courseId, hasPrerequisite, mustBeOffered, courseName, courseCredits, maximumEnrollment, currentEnrollment, priority);

            if (response == null) {
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
    @Path("/update")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response update(@FormParam("uuid") String uuid,
                           @FormParam("courseId") String courseId,
                           @FormParam("hasPrerequisite") String hasPrerequisite,
                           @FormParam("mustBeOffered") String mustBeOffered,
                           @FormParam("courseName") String courseName,
                           @FormParam("courseCredits") String courseCredits,
                           @FormParam("priority") String priority,
                           @FormParam("maximumEnrollment") String maximumEnrollment,
                           @FormParam("currentEnrollment") String currentEnrollment) {

        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.updateCourse(uuid, courseId, hasPrerequisite, mustBeOffered, courseName, courseCredits, maximumEnrollment, currentEnrollment, priority);
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
    @Path("/addPrerequisiteToCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addPrerequisiteToCourse(@FormParam("courseUUID1") String courseUUID1, @FormParam("courseUUID2") String courseUUID2) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.addPrerequisiteToCourse(courseUUID1, courseUUID2);
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
    @Path("/removePrerequisiteToCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removePrerequisiteToCourse(@FormParam("courseUUID1") String courseUUID1, @FormParam("courseUUID2") String courseUUID2) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.removePrerequisiteToCourse(courseUUID1, courseUUID2);
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
    @Path("/setCourseSemester")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response setCourseSemester(@FormParam("courseUUID") String courseUUID, @FormParam("semesterUUID") String semesterUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.setCourseSemester(courseUUID, semesterUUID);
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
    @Path("/removeCourseSemester")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeCourseSemester(@FormParam("courseUUID") String courseUUID, @FormParam("semesterUUID") String semesterUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.removeCourseSemester(courseUUID, semesterUUID);
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
    @Path("/deleteCourseByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response deleteCourseByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            CourseManager courseManager = new CourseManager();
            String response = courseManager.deleteCourseByUUID(uuid);
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
    @Path("/course/{uuid}")
    public Response getCourse(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        CourseManager courseManager = new CourseManager();


        try {
            Course course = courseManager.getCourseByUUID(uuid);

            if (course != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(course);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + course + " does not exist");
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
    @Path("/allCourses")
    public Response getAll() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        CourseManager courseManager = new CourseManager();


        try {
            List<Course> course = courseManager.getAll();

            if (course != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(course);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + course + " does not exist");
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


}
