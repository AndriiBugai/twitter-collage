package app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CollageRequest(

        @NotBlank
        String login,

        @NotNull @Min(4) @Max(100)
        Integer size,

        @Min(25) @Max(100)
        Integer tileSize
) {
    public int tileSizeOrDefault() {
        return tileSize != null ? tileSize : 64;
    }
}
