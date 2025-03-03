package com.endava.project;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.endava.user.User;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Entity
@Table(name = "projects", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "user_id" })
})
public class Project extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    public ZonedDateTime created;

    @ManyToOne
    public User user;

    @Version
    public int version;

}
