package com.tcc.produto_service.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.tcc.common.grpc.product.ProductServiceGrpc;
import com.tcc.common.grpc.product.ProductResponse;
import com.tcc.common.grpc.product.ListProductsRequest;
import com.tcc.common.grpc.product.ListProductsResponse;
@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void listProducts(ListProductsRequest request, StreamObserver<ListProductsResponse> responseObserver) {
        ProductResponse p = ProductResponse.newBuilder()
                .setId("1")
                .setName("Produto Teste TCC")
                .setPrice(99.90)
                .build();
        
        ListProductsResponse res = ListProductsResponse.newBuilder().addProducts(p).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
