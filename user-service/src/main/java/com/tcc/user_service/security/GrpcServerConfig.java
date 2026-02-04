package com.tcc.user_service.security;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Bean
    GrpcServerConfigurer securityInterceptorConfigurer(ServerInterceptor grpcJwtInterceptor) {

        return serverBuilder -> serverBuilder.intercept(grpcJwtInterceptor);
    }
}
