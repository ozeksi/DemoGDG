package com.ozeksi;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MockApi {
    @GET()
    @Path("/test")
    @Produces("text/plain")
    public String getMsgBase() {
        return "Hello Resource";
    }

    @GET()
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getUser() throws IOException {
        System.out.println("getUser");
        return ResourceManager.readJsonFile("User");
    }
}
