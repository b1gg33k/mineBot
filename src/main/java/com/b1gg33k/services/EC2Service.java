package com.b1gg33k.services;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StartInstancesResponse;

public class EC2Service {
    static EC2Service instance = null;
    private Ec2Client client = null;

    public EC2Service() {
        if (null == EC2Service.instance) {
            EC2Service.instance = new EC2Service();
            EC2Service.instance.client = Ec2Client.create();
        }
    }

    public void startInstance(String instanceId, ServiceCallback callback) {
        StartInstancesRequest request = StartInstancesRequest.builder()
                .instanceIds(instanceId).build();

        StartInstancesResponse response = client.startInstances(request);
    }
}
