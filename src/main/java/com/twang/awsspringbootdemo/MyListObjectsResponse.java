package com.twang.awsspringbootdemo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@JacksonXmlRootElement(localName = "ListObjectsResponse")
public class MyListObjectsResponse {

    @JacksonXmlProperty(localName = "IsTruncated")
    private Boolean truncated;

    @JacksonXmlProperty(localName = "Marker")
    private String marker;

    @JacksonXmlProperty(localName = "NextMarker")
    private String nextMarker;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Contents")
    private List<MyS3Object> contents;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    @JacksonXmlProperty(localName = "Delimiter")
    private String delimiter;

    @JacksonXmlProperty(localName = "MaxKeys")
    private Integer maxKeys;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CommonPrefixes")
    private List<MyCommonPrefix> commonPrefixes;

    @JacksonXmlProperty(localName = "EncodingType")
    private String encodingType;

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    @XmlElement(name = "Marker")
    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public List<MyS3Object> getContents() {
        return contents;
    }

    public void setContents(List<MyS3Object> contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(Integer maxKeys) {
        this.maxKeys = maxKeys;
    }

    public List<MyCommonPrefix> getCommonPrefixes() {
        return commonPrefixes;
    }

    public void setCommonPrefixes(List<MyCommonPrefix> commonPrefixes) {
        this.commonPrefixes = commonPrefixes;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }
}
