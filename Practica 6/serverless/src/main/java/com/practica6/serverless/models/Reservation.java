package com.practica6.serverless.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.xspec.S;

import java.time.LocalDateTime;

@DynamoDBTable(tableName="Reservation")
public class Reservation {
    @DynamoDBHashKey(attributeName="uuid")
    private String uuid;
    @DynamoDBAttribute(attributeName = "enrollment")
    private String enrollment;
    @DynamoDBAttribute(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName="career")
    private String career;
    @DynamoDBAttribute(attributeName="lab")
    private String lab;
    @DynamoDBAttribute(attributeName="date")
    private String date;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "uuid='" + uuid + '\'' +
                ", enrollment='" + enrollment + '\'' +
                ", name='" + name + '\'' +
                ", career='" + career + '\'' +
                ", lab='" + lab + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
