package ua.bohdan.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotNull(message = "Category field 'NAME' can't be NULL")
    @Size(min = 3)
    private String name;
}
