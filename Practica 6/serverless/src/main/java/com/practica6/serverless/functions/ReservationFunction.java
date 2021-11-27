package com.practica6.serverless.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.practica6.serverless.models.ListReservarionResponse;
import com.practica6.serverless.models.Reservation;
import com.practica6.serverless.services.ReservationService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ReservationFunction implements RequestStreamHandler {

    private Gson gson = new Gson();
    private ReservationService reservationService = new ReservationService();

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String body = null;
        JSONObject responseJson = new JSONObject();
        String response = "";
        Reservation reservation = null;

        try{
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            context.getLogger().log(""+jsonObject.toJSONString());

            //Recuperando el metodo de acceso de la llamada del API.
            if(jsonObject.get("requestContext")==null){
                throw new IllegalArgumentException("No respesta el API de entrada");
            }
            String HttpMethod = jsonObject.get("httpMethod").toString();
            switch (HttpMethod){
                case "GET":
                    ListReservarionResponse listReservarionResponse = reservationService.findAll(context);
                    response = gson.toJson(listReservarionResponse);
                    break;
                case "POST":
                case "PUT":
                    reservation = reservationService.getReservationBodyJson(jsonObject);

                    response = gson.toJson(reservationService.save(reservation,context));
                    break;
            }

            if(jsonObject.get("body") != null){
                body = jsonObject.get("body").toString();
            }
            JSONObject responseBody = new JSONObject();
            responseBody.put("data", JsonParser.parseString(response));
            //responseBody.put("entrada", context);

            JSONObject headerJson = new JSONObject();
            headerJson.put("Content-Type", "application/json");
            //TODO mandar header
            headerJson.put("Access-Control-Allow-Origin", "*");


            responseJson.put("statusCode", 200);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());


        } catch (Exception e) {
            responseJson.put("statusCode", 500);
            responseJson.put("exception", e);
            responseJson.put("message", e.getMessage());
        }

        //Salida
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }
}
