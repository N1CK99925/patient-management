package com.hopital.billing_service.grpc;

import org.slf4j.Logger;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingServiceGrpc extends BillingServiceImplBase {
    
    // TODO: add logger

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
            StreamObserver<BillingResponse> responseObserver) {
             
            // Buisness Logic - eg. save database 

            BillingResponse response = BillingResponse.newBuilder()
            .SetAccoun
            .setStatus("ACTIVE")
            .buildOf


    }

}
