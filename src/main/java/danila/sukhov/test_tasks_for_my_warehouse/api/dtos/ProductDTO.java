package danila.sukhov.test_tasks_for_my_warehouse.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {

    @NonNull
    Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name;

    @Size(max = 4096, message = "Description must not exceed 4096 characters")
    String description;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    Double cost;

    @Builder.Default
    Boolean existence = false;

}
