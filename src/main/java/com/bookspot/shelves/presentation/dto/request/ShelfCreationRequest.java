package com.bookspot.shelves.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShelfCreationRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    private Boolean isPublic;
}
