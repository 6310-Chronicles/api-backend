package com.cs6310.backend.api;

import com.cs6310.backend.cms.AdministratorManager;
import com.cs6310.backend.model.Administrator;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/administrator")
public class AdminstratorAPI {

    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(payload);


        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/update")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(payload);

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/addRoleToAdministrator")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addRoleToAdministrator(@FormParam("adminUUID") String adminUUID, @FormParam("roleUUID") String roleUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            AdministratorManager administratorManager = new AdministratorManager();
            String response = administratorManager.addRoleToAdministrator(adminUUID, roleUUID);
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
    @Path("/removeRoleFromAdministrator")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removeRoleFromAdministrator(@FormParam("adminUUID") String adminUUID, @FormParam("roleUUID") String roleUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            AdministratorManager administratorManager = new AdministratorManager();
            String response = administratorManager.removeRoleFromAdministrator(adminUUID, roleUUID);
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
    @Path("/allAdminstrators")
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
    @Path("/administrator/{uuid}")
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
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @POST
    @Path("/deleteAdminByUUID")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response deleteCourseByUUID(@FormParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


}
