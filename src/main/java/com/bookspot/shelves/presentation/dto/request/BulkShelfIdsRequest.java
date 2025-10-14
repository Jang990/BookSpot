package com.bookspot.shelves.presentation.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BulkShelfIdsRequest {
    @Size(min = 1, max = 50)
    private List<Long> shelfIds;
}
