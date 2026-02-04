package com.tcc.produto_service.service;


import com.tcc.common.grpc.product.ListProductsRequest;
import com.tcc.common.grpc.product.ListProductsResponse;
import com.tcc.common.grpc.product.ProductResponse;
import com.tcc.common.grpc.product.ProductServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {

    @Override
    public void listProducts(ListProductsRequest request, StreamObserver<ListProductsResponse> responseObserver) {


        ProductResponse p1 = ProductResponse.newBuilder()
                .setId("101")
                .setName("Teclado Mecânico")
                .setPrice(250.00)
                .build();


        ListProductsResponse response = ListProductsResponse.newBuilder()
                .addProducts(p1)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}