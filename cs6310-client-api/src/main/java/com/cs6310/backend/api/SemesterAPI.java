package com.cs6310.backend.api;

import com.cs6310.backend.cms.SemesterManager;
import com.cs6310.backend.model.Semester;
import com.cs6310.backend.response.APIResponse;
import com.cs6310.backend.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/6/15.
 */
@Path("/semester")
public class SemesterAPI {

//http://jsfiddle.net/clickthelink/Uwcuz/1/

    @POST
    @Path("/create")
    @Consumes({"application/json", "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"})
    public Response create(
            @FormParam("name") String name, @FormParam("year") String year) {
        SemesterManager semesterManager = new SemesterManager();
        String error = semesterManager.addSemester(name, year);

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
    @Path("/semesters")
    public Response getSemesters() {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        SemesterManager semesterManager = new SemesterManager();
        try {
            List roles = semesterManager.getAllSemesters();

            if (roles != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(new ArrayList<Semester>(roles));
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause("Failed to fetch Semesters");
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
    @Path("/semester/{uuid}")
    public Response getSemester(@PathParam("uuid") String uuid) {
        APIResponse payload = new APIResponse();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        SemesterManager semesterManager = new SemesterManager();

        try {
            Semester semester = semesterManager.getSemester(uuid);

            if (semester != null) {
                payload.setStatus(ResponseStatus.OK);
                payload.setResult(semester);
            } else {
                payload.setStatus(ResponseStatus.FAILED);
                payload.setErrorCause("Role with name :" + semester + " does not exist");
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

}
