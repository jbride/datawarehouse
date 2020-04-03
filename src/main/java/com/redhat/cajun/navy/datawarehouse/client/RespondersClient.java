package com.redhat.cajun.navy.datawarehouse.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.redhat.cajun.navy.datawarehouse.model.Responder;

import java.util.Set;

@Path("")
@RegisterRestClient
public interface RespondersClient {

    @GET
    @Path("/responders/available")
    @Produces("application/json")
    Set<Responder> available();

    @GET
    @Path("/responder/byname/{name}")
    @Produces("application/json")
    Responder getByName(@PathParam String name);

    @GET
    @Path("/responder/{id}")
    @Produces("application/json")
    Responder getById(@PathParam int id);
}