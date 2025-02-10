package com.api.gateway.config;

import com.api.gateway.service.DynamicRouteService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RouteRefresher {
    private final DynamicRouteService dynamicRouteService;

    public RouteRefresher(DynamicRouteService dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    @Scheduled(fixedRate = 30000)
    public void refreshRoutes() {
        System.out.println("Refreshing API Gateway routes....");
        dynamicRouteService.loadRoutes();
    }
}
