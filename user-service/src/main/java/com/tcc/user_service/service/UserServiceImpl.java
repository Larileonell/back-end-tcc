package com.tcc.user_service.service;
import com.tcc.common.grpc.user.GetUserRequest;
import com.tcc.common.grpc.user.UserResponse;
import com.tcc.common.grpc.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> responseObserver) {

        UserResponse response = UserResponse.newBuilder()
                .setId(request.getId())
                .setName("Usuário Validado PR1")
                .setEmail("contato@tcc.com")
                .build();


        responseObserver.onNext(response);


        responseObserver.onCompleted();
    }
}
