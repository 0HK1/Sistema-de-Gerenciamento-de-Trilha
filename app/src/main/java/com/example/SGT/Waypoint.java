package com.example.SGT;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Waypoint {
    private long id;
    private double latitude, longitude, altitude, velocidadeMedia, distancia;
    private Date date;
    private String dateString;

    public Waypoint() {
        this.id = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.altitude = 0.0;
        this.velocidadeMedia = 0.0;
        this.distancia = 0.0;
        this.date = new Date();  // Data atual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateString = sdf.format(this.date);  // Data formatada como String
    }

    public Waypoint(Location location) {
        this.id = 0;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.altitude = location.getAltitude();
        this.velocidadeMedia = 0.0;
        this.distancia = 0.0;
        this.date = new Date();  // Data atual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateString = sdf.format(this.date);  // Data formatada como String
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public double getVelocidadeMedia() {
        return velocidadeMedia;
    }

    public void setVelocidadeMedia(double velocidadeMedia) {
        this.velocidadeMedia = velocidadeMedia;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateString = sdf.format(this.date);  // Atualiza a data formatada
    }
}
