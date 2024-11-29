package danila.sukhov.test_tasks_for_my_warehouse.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @NotBlank(message = "Name can't be empty")
    @Size(max = 255, message = "It's too long name")
    String name;

    @Size(max = 4096, message = "It's too long description")
    String description;

    @Min(value = 0, message = "Cost can't be cannot be less than zero")
    Double cost;

    Boolean existence;


}
