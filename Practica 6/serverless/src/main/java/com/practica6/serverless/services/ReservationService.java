package com.practica6.serverless.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.practica6.serverless.models.ListReservarionResponse;
import com.practica6.serverless.models.Reservation;
import com.practica6.serverless.models.ReservationResponse;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservationService {

    public ReservationResponse save(Reservation reservation,Context context){
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        try {
            DynamoDBMapper mapper = new DynamoDBMapper(ddb);

            mapper.save(generateUUID(reservation));
        }catch (Exception e){
            return new ReservationResponse(true,e.getMessage(),null);
        }

        return new ReservationResponse(false,null,reservation);
    }

    public ListReservarionResponse findAll(Context context) {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        List<Reservation> reservations = new ArrayList<>();

            ScanRequest scanRequest = new ScanRequest().withTableName(System.getenv("TABLE"));

            ScanResult result = null;
            try{
                do {// La consulta vía ScanRequest solo retorna 1 MB de datos por iteracion,
                    //debemos iterar.

                        if (result != null) {
                            scanRequest.setExclusiveStartKey(result.getLastEvaluatedKey());
                        }

                        result = ddb.scan(scanRequest);
                        List<Map<String, AttributeValue>> rows = result.getItems();

                    // Iterando todos los elementos
                    for (Map<String, AttributeValue> mapReservation : rows) {
                        System.out.println("" + mapReservation);
                        //
                        AttributeValue id = mapReservation.get("uuid");
                        AttributeValue name = mapReservation.get("name");
                        AttributeValue enrollment = mapReservation.get("enrollment");
                        AttributeValue career = mapReservation.get("career");
                        AttributeValue lab = mapReservation.get("lab");
                        AttributeValue date = mapReservation.get("date");
                        //
                        Reservation tmp = new Reservation();
                        tmp.setUuid(id.getS());
                        if (name != null) {
                            tmp.setName(name.getS());
                        }
                        if (career != null) {
                            tmp.setCareer(career.getS());
                        }
                        if (lab != null) {
                            tmp.setLab(lab.getS());
                        }

                        if (date != null) {
                            tmp.setDate(date.getS());
                        }
                        if (enrollment != null) {
                            tmp.setEnrollment(enrollment.getS());
                        }
                        //
                        reservations.add(tmp);
                    }


                } while (result.getLastEvaluatedKey() != null);

            }catch (Exception e){
            return new ListReservarionResponse(true, e.getMessage(),reservations );
        }

        return new ListReservarionResponse(false, "", reservations);
    }

    public Reservation generateUUID(Reservation reservation){
        reservation.setUuid(UUID.randomUUID().toString());
        return reservation;
    }

    public Reservation getReservationBodyJson(JSONObject json) throws IllegalArgumentException{
        Gson gson = new Gson();
        if(json.get("body")==null){
            throw new IllegalArgumentException("No envio el cuerpo en la trama.");
        }
        Reservation reservation = gson.fromJson(json.get("body").toString(), Reservation.class);
        return reservation;
    }

}
