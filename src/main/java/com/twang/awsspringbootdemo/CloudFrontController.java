package com.twang.awsspringbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudfront.CloudFrontClient;
import software.amazon.awssdk.services.cloudfront.model.CreateInvalidationRequest;
import software.amazon.awssdk.services.cloudfront.model.CreateInvalidationResponse;
import software.amazon.awssdk.services.cloudfront.model.InvalidationBatch;
import software.amazon.awssdk.services.cloudfront.model.ListInvalidationsRequest;
import software.amazon.awssdk.services.cloudfront.model.ListInvalidationsResponse;
import software.amazon.awssdk.services.cloudfront.model.Paths;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cdn")
public class CloudFrontController {

    @GetMapping(value = "/create/invalidation/{distributionId}")
    public String createInvalidation(@PathVariable String distributionId,
                                     @RequestParam String path) {
        String[] pathArray = path.split(",");
        List<String> pathList = Arrays.asList(pathArray);
        CloudFrontClient client = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)
                .build();

        CreateInvalidationRequest request = CreateInvalidationRequest.builder()
                .distributionId(distributionId)
                .invalidationBatch(InvalidationBatch.builder()
                        .callerReference("" + System.currentTimeMillis())
                        .paths(Paths.builder().items(pathList).quantity(pathList.size()).build()).build())
                .build();
        CreateInvalidationResponse response = client.createInvalidation(request);
        System.out.println("response location= " + response.location());
        System.out.println("response invalidationId = " + response.invalidation().id());
        System.out.println("response callerReference = " + response.invalidation().invalidationBatch().callerReference());
        System.out.println("response paths = " + response.invalidation().invalidationBatch().paths().toString());
        return response.invalidation().status();
    }

    @GetMapping("/list/invalidation/{distributionId}")
    public String listInvalidations(@PathVariable String distributionId) {
        CloudFrontClient client = CloudFrontClient.builder()
                .region(Region.AWS_GLOBAL)
                .build();

        ListInvalidationsRequest request = ListInvalidationsRequest.builder()
                .distributionId(distributionId).build();
        ListInvalidationsResponse response = client.listInvalidations(request);
        System.out.println("response = " + response);
        return response.toString();
    }
}
