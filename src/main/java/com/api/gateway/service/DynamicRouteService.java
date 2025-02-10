package com.api.gateway.service;

import com.api.gateway.model.RouteEntity;
import com.api.gateway.repository.RouteRepository;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;


@Service
public class DynamicRouteService {
    private final RouteRepository routeRepository;
    private final RouteDefinitionWriter routeDefinitionWriter;

    public DynamicRouteService(RouteRepository routeRepository, @Lazy RouteDefinitionWriter routeDefinitionWriter) {
        this.routeRepository = routeRepository;
        this.routeDefinitionWriter = routeDefinitionWriter;
    }
    public void loadRoutes() {
        List<RouteEntity> routes = routeRepository.findByActiveTrue();

        routes.forEach(route -> {
            RouteDefinition definition = new RouteDefinition();
            definition.setId(route.getServiceName());
            definition.setUri(URI.create(route.getUri()));

            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setName("Path");
            predicate.setArgs(Collections.singletonMap(NameUtils.generateName(0), route.getPath()));

            definition.setPredicates(Collections.singletonList(predicate));

            routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        });
    }
}