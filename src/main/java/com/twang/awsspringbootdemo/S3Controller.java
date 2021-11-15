package com.twang.awsspringbootdemo;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {

    @GetMapping("/buckets")
    public String getAllBuckets() {

        S3Client s3Client = S3Client.builder()
                .region(Region.US_WEST_1)
                .build();

        ListBucketsResponse response = s3Client.listBuckets();
        return response.buckets().toString();
    }

    @GetMapping("/list/{bucketName}/{filePath}")
    public String getFileList(@PathVariable String bucketName, @PathVariable String filePath,
                              @RequestParam(required = false) String region) {
        System.out.println("filePath = " + filePath);

        S3Client s3Client = S3Client.builder()
                .region(StringUtils.hasText(region) ? Region.of(region) : Region.US_WEST_2)
                .build();

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        List<S3Object> contents = response.contents();
        contents.forEach(obj -> {
            System.out.println("key = " + obj.key());
        });
        return contents.toString();
    }

}
