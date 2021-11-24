package com.twang.awsspringbootdemo;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/s3")
public class S3FileController {

    @GetMapping("/download/{bucketName}")
    public String download(@PathVariable String bucketName,
                           @RequestParam String filePath,
                           @RequestParam(required = false) String region) throws Exception {
        System.out.println("filePath = " + filePath);

        S3Client s3Client = S3Client.builder()
                .region(StringUtils.hasText(region) ? Region.of(region) : Region.US_WEST_1)
                .build();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();

        ResponseBytes<GetObjectResponse> bytes = s3Client.getObjectAsBytes(request);
        byte[] data = bytes.asByteArray();

        File myFile = new File("/Users/twang/IdeaProjects/aws-springboot-demo/" + filePath);
        File parentFile = myFile.getParentFile();
        System.out.println("parentFile = " + parentFile.getPath());
        boolean mkdirs = parentFile.mkdirs();
        System.out.println("mkdirs = " + mkdirs);

        boolean result = myFile.createNewFile();
        System.out.println("result = " + result);
        try (OutputStream os = new FileOutputStream(myFile)) {

            os.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes.response().toString();
    }

    @GetMapping("/upload/{bucketName}")
    public String upload(@PathVariable String bucketName,
                         @RequestParam String filePath,
                         @RequestParam(required = false) String targetPath,
                         @RequestParam(required = false) String region) throws Exception {
        System.out.println("filePath = " + filePath);

        S3Client s3Client = S3Client.builder()
                .region(StringUtils.hasText(region) ? Region.of(region) : Region.US_WEST_1)
                .build();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("my-meta-key", "my-meta-val");

        File sourceFile = new File(filePath);

        String objectKey;
        if (StringUtils.hasText(targetPath)) {
            objectKey = targetPath;
        } else {
            objectKey = sourceFile.getName();
        }
        System.out.println("objectKey = " + objectKey);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .metadata(metadata)
                .build();

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromFile(sourceFile));

        return response.toString();
    }
}
