package com.endava.auth;

import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/auth")
public class AuthResource {

    private final AuthService authService;

    @Inject
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @GET
    public Uni<String> hello() {
        return Uni.createFrom().item("Working");
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(200)
    @Path("login")
    public Uni<String> login(AuthRequest authRequest) {
        return authService.authenticate(authRequest);
    }

}
