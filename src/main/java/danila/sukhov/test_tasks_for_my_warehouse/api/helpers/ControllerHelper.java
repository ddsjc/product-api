package danila.sukhov.test_tasks_for_my_warehouse.api.helpers;

import danila.sukhov.test_tasks_for_my_warehouse.api.exceptions.NotFoundException;
import danila.sukhov.test_tasks_for_my_warehouse.store.entities.ProductEntity;
import danila.sukhov.test_tasks_for_my_warehouse.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllerHelper {
    ProductRepository projectRepository;
    public ProductEntity getProductOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Name not found", projectId))
                );

    }
}
