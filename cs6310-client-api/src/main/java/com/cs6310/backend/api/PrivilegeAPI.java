package com.cs6310.backend.api;

/**
 * Created by nelson on 11/6/15.
 */

import com.cs6310.backend.cms.PrivilegeManager;
import com.cs6310.backend.model.Privilege;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/privilege")
public class PrivilegeAPI {

    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response create(
            @FormParam("name") String name) {
        PrivilegeManager privilegeManager = new PrivilegeManager();
        String error = privilegeManager.addPrivilege(name);
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
    @Path("/remove")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response remove(
            @FormParam("uuid") String uuid) {
        PrivilegeManager privilegeManager = new PrivilegeManager();
        String error = privilegeManager.deletePrivilege(uuid);
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
    @Path("/dropAll")
    public Response dropAll() {
        APIResponse payload = new APIResponse();
        try {
            PrivilegeManager privilegeManager = new PrivilegeManager();
            if (privilegeManager.deleteAllPrivileges() != null) {
                payload.setStatus(ResponseStatus.OK);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
            }
        } catch (Exception e) {
            payload.setStatus(ResponseStatus.FAILED);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @GET
    @Path("/privilege/{uuid}")
    public Response getPrivilege(@PathParam("uuid") String uuid) {

        APIResponse payload = new APIResponse();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        PrivilegeManager privilegeManager = new PrivilegeManager();

        Privilege privilege = privilegeManager.getPrivilege(uuid);

        if (privilege != null) {
            payload.setStatus(ResponseStatus.OK);
            payload.setResult(privilege);
        } else {
            payload.setStatus(ResponseStatus.FAILED);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


    @GET
    @Path("/privilegeAll")
    public Response getAllPrivilege() {

        APIResponse payload = new APIResponse();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        PrivilegeManager privilegeManager = new PrivilegeManager();

        List privilege = privilegeManager.getAllPrivileges();

        if (privilege != null) {
            payload.setStatus(ResponseStatus.OK);
            payload.setResult(privilege);
        } else {
            payload.setStatus(ResponseStatus.FAILED);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }


}
