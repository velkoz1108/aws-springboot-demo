package com.twang.awsspringbootdemo;

import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CommonPrefix;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class S3Controller {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    public MyListObjectsResponse index(@RequestParam(required = false, defaultValue = "/") String delimiter,
                                       @RequestParam(required = false, defaultValue = "") String prefix) {
        System.out.println("delimiter = " + delimiter);
        System.out.println("prefix = " + prefix);
        if ("index.html/".equals(prefix)) prefix = "";
        S3Client s3Client = S3Client.builder()
                .region(Region.US_WEST_1)
                .build();

        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket("dev-twang")
                .delimiter(delimiter)
                .prefix(prefix)
                .build();

        ListObjectsResponse response = s3Client.listObjects(request);

        List<MyS3Object> list = new ArrayList<>();
        if (response.hasContents()) {
            for (S3Object content : response.contents()) {
                MyS3Object myS3Object = new MyS3Object();
                BeanUtils.copyProperties(content, myS3Object);
                list.add(myS3Object);
            }
        }
        List<MyCommonPrefix> commonPrefixes = new ArrayList<>();
        if (response.hasCommonPrefixes()) {
            for (CommonPrefix commonPrefix : response.commonPrefixes()) {
                MyCommonPrefix myCommonPrefix = new MyCommonPrefix();
                BeanUtils.copyProperties(commonPrefix, myCommonPrefix);
                commonPrefixes.add(myCommonPrefix);
            }
        }


        MyListObjectsResponse myListObjectsResponse = new MyListObjectsResponse();
        BeanUtils.copyProperties(response, myListObjectsResponse);
        myListObjectsResponse.setContents(list);
        myListObjectsResponse.setCommonPrefixes(commonPrefixes);
        return myListObjectsResponse;
    }

    @GetMapping("/buckets")
    public String getAllBuckets() {

        S3Client s3Client = S3Client.builder()
                .region(Region.US_WEST_1)
                .build();

        ListBucketsResponse response = s3Client.listBuckets();
        return response.buckets().toString();
    }


    @GetMapping("/test")
    public String test() {

        S3Client s3Client = S3Client.builder()
                .region(Region.US_WEST_1)
                .build();

        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket("dev-twang")
                .delimiter("/")
                .prefix("dir1/")
                .build();

        ListObjectsResponse response = s3Client.listObjects(request);
        System.out.println("response = " + response.toString());
        System.out.println(" ================ ");
        for (S3Object content : response.contents()) {
            System.out.println("content = " + content.toString());
        }

        for (CommonPrefix commonPrefix : response.commonPrefixes()) {
            System.out.println("commonPrefix = " + commonPrefix.toString());
        }

        return "success";
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
        System.out.println("response = " + response.toString());
        List<S3Object> contents = response.contents();
        contents.forEach(obj -> {
            System.out.println("obj = " + obj.toString());
        });

        return contents.toString();
    }

}
