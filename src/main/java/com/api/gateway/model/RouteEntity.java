package com.api.gateway.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gateway_routes")

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String path;
    private String uri;
    private boolean active;

    public Long getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPath() {
        return path;
    }

    public String getUri() {
        return uri;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
