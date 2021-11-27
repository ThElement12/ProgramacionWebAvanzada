package com.practica6.serverless.models;

import java.util.List;

public class ListReservarionResponse {
    private Boolean error;
    private String message;
    private List<Reservation> reservations;

    public ListReservarionResponse(Boolean error, String message, List<Reservation> reservations) {
        this.error = error;
        this.message = message;
        this.reservations = reservations;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
