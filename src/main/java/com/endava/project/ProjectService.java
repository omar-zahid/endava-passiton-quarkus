package com.endava.project;

import java.util.List;

import org.hibernate.ObjectNotFoundException;

import com.endava.task.Task;
import com.endava.user.UserService;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectService {

    private UserService userService;

    public ProjectService(UserService userService) {
        this.userService = userService;
    }

    public Uni<Project> findById(long id) {
        return userService.getCurrentUser()
                .chain(user -> Project.<Project>findById(id)
                        .onItem().ifNull().failWith(() -> new ObjectNotFoundException(id, "Project"))
                        .onItem()
                        .invoke(project -> {
                            if (!user.equals(project.user)) {
                                throw new UnauthorizedException("You are not allowed to update this project");
                            }
                        }));
    }

    public Uni<List<Project>> listForUser() {
        return userService.getCurrentUser()
                .chain(user -> Project.find("user", user).list());
    }

    @WithTransaction
    public Uni<Project> create(Project project) {
        return userService.getCurrentUser()
                .chain(user -> {
                    project.user = user;
                    return project.persistAndFlush();
                });
    }

    @WithTransaction
    public Uni<Project> update(Project project) {
        return findById(project.id)
                .chain(p -> Project.getSession())
                .chain(s -> s.merge(project));
    }

    @WithTransaction
    public Uni<Void> delete(long id) {
        return findById(id)
                .chain(project -> Task.update("project = null where project = ?1", project)
                        .chain(i -> project.delete()));
    }

}
