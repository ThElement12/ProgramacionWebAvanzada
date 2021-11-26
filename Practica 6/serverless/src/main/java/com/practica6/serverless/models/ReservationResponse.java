package com.practica6.serverless.models;

public class ReservationResponse {
    private Boolean error;
    private String message;
    private Reservation reservation;

    public ReservationResponse(Boolean error, String message, Reservation reservation) {
        this.error = error;
        this.message = message;
        this.reservation = reservation;
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
