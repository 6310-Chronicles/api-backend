package com.cs6310.backend.api;

import com.cs6310.backend.cms.RoleManager;
import com.cs6310.backend.model.Role;
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
@Path("/role")
public class RoleAPI {


    // P 0ea81731-d75d-4318-b91a-9ee35b432c56


    @POST
    @Path("/role")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response newRole(@FormParam("roleName") String name) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            RoleManager upm = new RoleManager();
            String response = upm.addRole(name);
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
    @Path("/addPrivilegeToRole")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response addPrivilegeToRole(@FormParam("roleUUID") String roleUUID, @FormParam("privilegeUUID") String privilegeUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            RoleManager upm = new RoleManager();
            String response = upm.addPrivilegeToRole(roleUUID, privilegeUUID);
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
    @Path("/removePrivilegeFromRole")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response removePrivilegeFromRole(@FormParam("roleUUID") String roleUUID, @FormParam("privilegeUUID") String privilegeUUID) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            RoleManager upm = new RoleManager();
            String response = upm.removePrivilegeFromRole(roleUUID, privilegeUUID);
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
    @Path("/allRoles")
    public Response getRoles() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        RoleManager upm = new RoleManager();
        try {
            List<Role> roles = upm.getAllRoles();

            if (roles != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(roles);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause("Failed to fetch roles");
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
    @Path("/role/{name}")
    public Response getRoles(@PathParam("name") String name) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        RoleManager upm = new RoleManager();
        try {
            Role role = upm.getRole(name);

            if (role != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(role);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause("Role with name :" + name + " does not exist");
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(payload, new TypeToken<APIResponse>() {
            }.getType())).build();
        } catch (Exception e) {
            payload = new APIResponse();
            payload.setStatus(ResponseStatus.FAILED);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(payload)).build();
        }
    }


    @GET
    @Path("/dropAll")
    public Response dropAll() {
        APIResponse payload = new APIResponse();
        try {
            RoleManager privilegeManager = new RoleManager();
            if (privilegeManager.deleteAllRoles()) {
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


    @POST
    @Path("/remove")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response remove(
            @FormParam("uuid") String uuid) {

        RoleManager privilegeManager = new RoleManager();
        String error = privilegeManager.deleteRole(uuid);
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


}
