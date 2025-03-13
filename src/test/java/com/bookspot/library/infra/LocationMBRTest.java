package com.bookspot.library.infra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationMBRTest {
    @Test
    void mbr쿼리_Where문을_생성() {
        LocationMBR mbr = new LocationMBR(
                0.0, 0.0,
                1.0, 1.0
        );

        assertEquals("""
                ST_Contains(
                    ST_GeomFromText(
                        'POLYGON((0.000000 0.000000, 1.000000 0.000000, 1.000000 1.000000, 0.000000 1.000000, 0.000000 0.000000))',
                        4326),
                    location
                )""", mbr.mbrContains("location"));
    }

}