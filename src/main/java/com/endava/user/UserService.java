package com.endava.user;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.hibernate.ObjectNotFoundException;

import com.endava.project.Project;
import com.endava.task.Task;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    private final JsonWebToken jwt;

    @Inject
    public UserService(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    public Uni<User> findById(long id) {
        return User.<User>findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new ObjectNotFoundException(id, "User"));
    }

    @WithTransaction
    public Uni<User> findByName(String name) {
        return User.find("name", name).firstResult();
    }

    public Uni<List<User>> list() {
        return User.findAll().list();
    }

    @WithTransaction
    public Uni<User> create(User user) {
        user.password = BcryptUtil.bcryptHash(user.password);
        return user.persistAndFlush();
    }

    @WithTransaction
    public Uni<User> update(User user) {
        return findById(user.id)
                .chain(u -> User.getSession())
                .chain(s -> s.merge(user));
    }

    @WithTransaction
    public Uni<Void> delete(long id) {
        return findById(id)
                .chain(user -> Uni.combine().all().unis(
                        Project.delete("user.id", user.id),
                        Task.delete("user.id", user.id)).asTuple().chain(t -> user.delete()));
    }

    public Uni<User> getCurrentUser() {
        return findByName(jwt.getName());
    }

    public static boolean matches(User user, String password) {
        return BcryptUtil.matches(password, user.password);
    }

}
