package com.tcc.user_service.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.tcc.common.grpc.user.UserServiceGrpc;
import com.tcc.common.grpc.user.GetUserRequest;
import com.tcc.common.grpc.user.UserResponse;
@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse response = UserResponse.newBuilder()
                .setId(request.getId())
                .setName("Usuario Teste TCC")
                .setEmail("tcc@pecege.com")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}