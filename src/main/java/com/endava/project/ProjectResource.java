package com.endava.project;

import java.util.List;

import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/projects")
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    public Uni<List<Project>> get() {
        return projectService.listForUser();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Project> create(Project project) {
        return projectService.create(project);
    }

    @GET
    @Path("{id}")
    public Uni<Project> get(@PathParam("id") long id) {
        return projectService.findById(id);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Project> update(@PathParam("id") long id, Project project) {
        project.id = id;
        return projectService.update(project);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(long id) {
        return projectService.delete(id);
    }

}
