package com.endava.user;

import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;

    @Column(nullable = false)
    String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    public List<String> roles;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    public ZonedDateTime created;

    @Version
    public int version;

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

}
