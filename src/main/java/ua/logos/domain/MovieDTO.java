package ua.logos.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String duration;
    private String ageRating;

    @NotNull(message = "Category can't be NULL")
    private CategoryDTO category;

    @DecimalMin(value = "1000", message = "Price MIN value '1000'")
    @DecimalMax(value = "1000000", message = "Price MAX value '1000000'")
    private BigDecimal price;

    @Min(value = 5, message = "Actors size >= 5")
    @Max(value = 50, message = "Actors size <= 5")
    private int actors;
}
