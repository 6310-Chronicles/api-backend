package com.cs6310.backend.api;

import com.cs6310.backend.cms.ProfessorManager;
import com.cs6310.backend.model.Professor;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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


        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String json = gson.toJson(payload);

        return Response.ok(json)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }


    @GET
    @Path("/allProfessors")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }


    @GET
    @Path("/professor/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }


    @POST
    @Path("/deleteProfessorByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteProfessorByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.deleteProfessorByUUID(uuid);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }


    }


    @POST
    @Path("/addProfCompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProfCompetentCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.addProfCompetentCourse(profUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }

    }


    @POST
    @Path("/removeProfCompetentCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeProfCompetentCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.removeProfCompetentCourse(profUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }

    }


    @POST
    @Path("/addProfTeachingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addProfTeachingCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();

        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.addProfTeachingCourse(profUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }


            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }

    }


    @POST
    @Path("/removeProfTeachingCourse")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeProfTeachingCourse(@FormParam("profUUID") String profUUID, @FormParam("courseUUID") String courseUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        try {
            ProfessorManager professorManager = new ProfessorManager();
            String response = professorManager.removeProfTeachingCourse(profUUID, courseUUID);
            if (response != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(response);
            }

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            payload.setStatus(ResponseStatus.FAILED);

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }


}
