package danila.sukhov.test_tasks_for_my_warehouse.api.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AskDTO {
    Boolean answer;
    public static AskDTO makeDefault(Boolean answer){
        return  builder()
                .answer(answer)
                .build();
    }
}
