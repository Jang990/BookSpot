package com.bookspot.library.infra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationMBRTest {
    @Test
    void mbr쿼리_Where문을_생성() {
        new LocationMBR(
                37.52739176387812, 126.75269026468787,
                37.50568658729097, 126.71657056097237
        );
    }

}