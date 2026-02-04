package com.tcc.user_service.security;
import io.grpc.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
public class GrpcJwtInterceptor implements ServerInterceptor {

    @Value("${JWT_SECRET:meusegredojwt123}")
    private String jwtSecret;

    private static final Metadata.Key<String> AUTHORIZATION =
            Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        String auth = headers.get(AUTHORIZATION);
        if (auth == null || !auth.toLowerCase().startsWith("bearer ")) {

            call.close(Status.UNAUTHENTICATED.withDescription("Token ausente ou inválido"), new Metadata());
            return new ServerCall.Listener<ReqT>() {};
        }

        try {
            String token = auth.substring(7).trim();
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return next.startCall(call, headers);
        } catch (Exception e) {
            call.close(Status.UNAUTHENTICATED.withDescription("Token Expirado ou Inválido"), new Metadata());
            return new ServerCall.Listener<ReqT>() {};
        }
    }
}
