package com.endava.user;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("users")
public class UserResource {

    @GET
    public Uni<String> hello() {
        return Uni.createFrom().item("Hello");
    }

}
