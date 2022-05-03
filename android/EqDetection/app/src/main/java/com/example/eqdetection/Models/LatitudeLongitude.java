package com.example.eqdetection.Models;

public class LatitudeLongitude {
    private double lat, lng;
    String markerInfo;

    public LatitudeLongitude(double lat, double lng, String markerInfo) {
        this.lat = lat;
        this.lng = lng;
        this.markerInfo = markerInfo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getMarkerInfo() {
        return markerInfo;
    }

    public void setMarkerInfo(String markerInfo) {
        this.markerInfo = markerInfo;
    }
}
