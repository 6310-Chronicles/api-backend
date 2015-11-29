package com.cs6310.backend.api;

import com.cs6310.backend.cms.TeachingAssistantManager;
import com.cs6310.backend.model.TeachingAssistant;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teachingAssistant")
public class TeachingAssistantAPI {


    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(
            @FormParam("studentId") String studentId) {


        TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();

        String error = teachingAssistantManager.addTeachingAssistant(studentId);

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
    @Path("/addTACompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addTACompetentCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.addTACompetentCourse(taUUID, courseUUID);
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
    @Path("/removeTACompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeTACompetentCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.removeTACompetentCourse(taUUID, courseUUID);
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
    @Path("/addTAAssistingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addTAAssistingCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.addTAAssistingCourse(taUUID, courseUUID);
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
    @Path("/removeTAAssistingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeTAAssistingCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.removeTAAssistingCourse(taUUID, courseUUID);
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
    @Path("/allTeachingAssistant")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllTeachingAssistant() {
        APIResponse payload = new APIResponse();
        TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();


        try {
            List<TeachingAssistant> list = teachingAssistantManager.getAllTeachingAssistants();

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
    @Path("/teachingAssistant/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdmin(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();

        TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();

        try {
            TeachingAssistant teachingAssistant = teachingAssistantManager.getTeachingAssistant(uuid);

            if (teachingAssistant != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(teachingAssistant);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + teachingAssistant + " does not exist");
            }

        } catch (Exception e) {
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
    @Path("/deleteTeachingAssistantByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteCourseByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        try {

            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();

            String response = teachingAssistantManager.deleteTAByUUID(uuid);

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


}
