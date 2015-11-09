package com.cs6310.backend.api;

import com.cs6310.backend.cms.TeachingAssistantManager;
import com.cs6310.backend.model.TeachingAssistant;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teachingAssistant")
public class TeachingAssistantAPI {


    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(payload);


        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/addTACompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addTACompetentCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.addTACompetentCourse(taUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }

    }

    @POST
    @Path("/removeTACompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeTACompetentCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.removeTACompetentCourse(taUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @POST
    @Path("/addTAAssistingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addTAAssistingCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.addTAAssistingCourse(taUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @POST
    @Path("/removeTAAssistingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeTAAssistingCourse(@FormParam("taUUID") String taUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();
            String response = teachingAssistantManager.removeTAAssistingCourse(taUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @GET
    @Path("/allTeachingAssistant")
    public Response getAllTeachingAssistant() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

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
    @Path("/teachingAssistant/{uuid}")
    public Response getAdmin(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

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
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @POST
    @Path("/deleteTeachingAssistantByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response deleteCourseByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {

            TeachingAssistantManager teachingAssistantManager = new TeachingAssistantManager();

            String response = teachingAssistantManager.deleteTAByUUID(uuid);

            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


}
