package com.bookspot.library.infra;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationMBR {
    private double nwLat;
    private double nwLon;
    private double seLat;
    private double seLon;

    public String mbrContains(String fieldName) {
        return """
                ST_Contains(
                    ST_GeomFromText(
                        'POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f))',
                        4326),
                    %s
                )""".formatted(
                        nwLat, nwLon, // 반시계 방향으로 사각형 생성
                        seLat, nwLon,
                        seLat, seLon,
                        nwLat, seLon,
                        nwLat, nwLon,
                        fieldName
                );
    }

}
