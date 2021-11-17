package com.twang.awsspringbootdemo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.Instant;

public class MyS3Object {
    @JacksonXmlProperty(localName = "Key")
    private String key;

    @JacksonXmlProperty(localName = "LastModified")
    private Instant lastModified;

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    @JacksonXmlProperty(localName = "Size")
    private Long size;

    @JacksonXmlProperty(localName = "StorageClass")
    private String storageClass;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }
}
