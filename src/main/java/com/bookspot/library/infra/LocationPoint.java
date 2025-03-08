package com.bookspot.library.infra;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationPoint {
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return "ST_GeomFromText('POINT(%f %f)', 4326)"
                .formatted(latitude, longitude);
    }
}
