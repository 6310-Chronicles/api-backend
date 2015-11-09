package com.cs6310.backend.api;

import com.cs6310.backend.cms.AuthenticatorManager;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/access")
public class AccessCredentialAPI {


    @POST
    @Path("/login")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response authenticate(@FormParam("username") String username,
                                 @FormParam("password") String password) {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        AuthenticatorManager upm = new AuthenticatorManager();
        Object object = upm.authenticate(username, password);
        APIResponse payload = new APIResponse();
        try {
            if (object == null) {

                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause("Authentication failed");
            } else {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
            payload.setStatus(ResponseStatus.FAILED);
            payload.setErrorCause(e.getMessage());
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(payload)).build();
    }

}
