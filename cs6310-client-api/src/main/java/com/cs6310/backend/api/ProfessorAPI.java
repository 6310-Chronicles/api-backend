package com.cs6310.backend.api;

import com.cs6310.backend.cms.ProfessorManager;
import com.cs6310.backend.model.Professor;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by nelson on 11/6/15.
 */
@Path("/professor")
public class ProfessorAPI {


    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response create(
            @FormParam("availability") String availability,
            @FormParam("profId") String profId,
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


        ProfessorManager professorManager = new ProfessorManager();

        String error = professorManager.addProf(profId, availability, firstName, lastName, "",
                mobilePhone, email, gender, address, userName, password, secretQuestion,
                secretAnswer, active);

        APIResponse payload = new APIResponse();
        if (error == null) {
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
            @FormParam("availability") String availability,
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


        ProfessorManager professorManager = new ProfessorManager();

        String error = professorManager.updateProf(uuid, availability, firstName, lastName, "",
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


    @GET
    @Path("/allProfessors")
    public Response getAllProfessors() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        ProfessorManager professorManager = new ProfessorManager();


        try {
            List<Professor> list = professorManager.getAllProfessors();

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
    @Path("/professor/{uuid}")
    public Response getProfessor(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        ProfessorManager professorManager = new ProfessorManager();

        try {
            Professor professor = professorManager.getProfessor(uuid);

            if (professor != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(professor);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + professor + " does not exist");
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
    @Path("/deleteProfessorByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response deleteProfessorByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.deleteProfessorByUUID(uuid);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

            return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }


    }


    @POST
    @Path("/addProfCompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addProfCompetentCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.addProfCompetentCourse(profUUID, courseUUID);
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
    @Path("/removeProfCompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeProfCompetentCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.removeProfCompetentCourse(profUUID, courseUUID);
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
    @Path("/addProfTeachingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addProfTeachingCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.addProfTeachingCourse(profUUID, courseUUID);
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
    @Path("/removeProfTeachingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeProfTeachingCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.removeProfTeachingCourse(profUUID, courseUUID);
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
