package danila.sukhov.test_tasks_for_my_warehouse.api.controllers;

import danila.sukhov.test_tasks_for_my_warehouse.api.dtos.AskDTO;
import danila.sukhov.test_tasks_for_my_warehouse.api.dtos.ProductDTO;
import danila.sukhov.test_tasks_for_my_warehouse.api.exceptions.BadRequestException;
import danila.sukhov.test_tasks_for_my_warehouse.api.factories.ProductDTOFactory;
import danila.sukhov.test_tasks_for_my_warehouse.api.helpers.ControllerHelper;
import danila.sukhov.test_tasks_for_my_warehouse.store.entities.ProductEntity;
import danila.sukhov.test_tasks_for_my_warehouse.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductDTOFactory productDTOFactory;
    ProductRepository productRepository;
    ControllerHelper controllerHelper;

    public static final String GET_PRODUCTS = "api/products";
    public static  final String CREATE_PRODUCT = "api/products/create";
    public  static  final  String UPDATE_PRODUCT = "api/products/update/{product_id}";
    public static final String DELETE_PRODUCT = "api/products/delete/{product_id}";

    @GetMapping(GET_PRODUCTS)
    public List<ProductDTO> getProducts(@RequestParam(value = "prefix_name",  required = false) Optional<String> optionalPrefixName){
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProductEntity> productEntityStream = optionalPrefixName
                .map(productRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(productRepository::streamAllBy);

        return  productEntityStream.map(productDTOFactory::createProductDto).collect(Collectors.toList());
    }

    @PostMapping(CREATE_PRODUCT)
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO){
        if (productDTO.getName().trim().isBlank()){
            throw new BadRequestException("Name can't be empty");
        }

        productRepository
                .findByName(productDTO.getName())
                .ifPresent(product -> {
                    throw new BadRequestException(String.format("Such a projectName \"%s\" already exists", productDTO.getName()));
        });

        ProductEntity product = productRepository.saveAndFlush(
                ProductEntity.builder()
                        .name(productDTO.getName())
                        .cost(productDTO.getCost())
                        .description(productDTO.getDescription())
                        .existence(productDTO.getExistence())
                        .build()
        );

        return productDTOFactory.createProductDto(product);
    }

    @PutMapping(UPDATE_PRODUCT)
    public ProductDTO updateProduct(@PathVariable("product_id") Long productId, @Valid @RequestBody ProductDTO productDTO){
        if(productDTO.getName().trim().isBlank()){
            throw new BadRequestException("Name can't be empty");
        }

        ProductEntity product = controllerHelper.getProductOrThrowException(productId);

        productRepository.findByName(productDTO.getName())
                .ifPresent(existingProduct -> {
                    if (!existingProduct.getId().equals(productId)) {
                        throw new BadRequestException(
                                String.format("A product with name \"%s\" already exists", productDTO.getName())
                        );
                    }
                });

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : product.getDescription());
        product.setExistence(productDTO.getExistence());
        product.setCost(productDTO.getCost() != null ? productDTO.getCost() : product.getCost());

        product = productRepository.saveAndFlush(product);

        return productDTOFactory.createProductDto(product);
    }

    @DeleteMapping(DELETE_PRODUCT)
    public AskDTO deleteProduct(@PathVariable ("product_id") Long productId){
        controllerHelper.getProductOrThrowException(productId);
        productRepository.deleteById(productId);
        return AskDTO.makeDefault(true);
    }

}
