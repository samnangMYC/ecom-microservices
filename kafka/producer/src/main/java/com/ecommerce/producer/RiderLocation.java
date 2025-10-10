package com.ecommerce.producer;

public class RiderLocation {
    private String RiderId;
    private double latitude;
    private double longitude;

    public RiderLocation() {}

    public RiderLocation(String riderId, double latitude, double longitude) {
        RiderId = riderId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRiderId() {
        return RiderId;
    }

    public void setRiderId(String riderId) {
        RiderId = riderId;
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
}
