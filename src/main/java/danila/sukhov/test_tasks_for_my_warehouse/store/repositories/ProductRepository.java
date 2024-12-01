package danila.sukhov.test_tasks_for_my_warehouse.store.repositories;

import danila.sukhov.test_tasks_for_my_warehouse.store.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    Stream<ProductEntity> streamAllBy();
    Stream<ProductEntity> streamAllByNameStartsWithIgnoreCase(String name);
}
