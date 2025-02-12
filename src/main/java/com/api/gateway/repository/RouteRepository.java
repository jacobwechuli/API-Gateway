package com.api.gateway.repository;

import com.api.gateway.model.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    List<RouteEntity> findByActiveTrue();
}
