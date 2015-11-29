package com.cs6310.backend.api;

import com.cs6310.backend.cms.AdministratorManager;
import com.cs6310.backend.model.Administrator;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/administrator")
public class AdminstratorAPI {

    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(
            @FormParam("administratorId") String administratorId,
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


        AdministratorManager administratorManager = new AdministratorManager();


        String error = administratorManager.addAdministrator(administratorId, firstName, lastName, "",
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
            @FormParam("administratorId") String administratorId,
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


        AdministratorManager administratorManager = new AdministratorManager();

        String error = administratorManager.updateAdministrator(uuid, administratorId, firstName, lastName, "",
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
    @Path("/allAdminstrators")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAdminstrators() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        AdministratorManager administratorManager = new AdministratorManager();


        try {
            List<Administrator> list = administratorManager.getAllAdminstrators();


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
    @Path("/administrator/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdmin(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        AdministratorManager administratorManager = new AdministratorManager();

        try {
            Administrator administrator = administratorManager.getAdministrator(uuid);

            if (administrator != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(administrator);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause(" This :" + uuid + " does not exist");
            }

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } catch (Exception e) {
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);

            String json = gson.toJson(payload);

            return Response.ok(json)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }


    @POST
    @Path("/deleteAdminByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteCourseByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        try {
            AdministratorManager administratorManager = new AdministratorManager();
            String response = administratorManager.deleteAdministratorByUUID(uuid);
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
