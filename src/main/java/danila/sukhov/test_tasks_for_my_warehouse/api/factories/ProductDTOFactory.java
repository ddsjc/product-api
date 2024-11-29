package danila.sukhov.test_tasks_for_my_warehouse.api.factories;

import danila.sukhov.test_tasks_for_my_warehouse.api.dtos.ProductDTO;
import danila.sukhov.test_tasks_for_my_warehouse.store.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOFactory {

    public ProductDTO createProductDto(ProductEntity productEntity){
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .cost(productEntity.getCost())
                .description(productEntity.getDescription())
                .existence(productEntity.getExistence())
                .build();
    }
}
