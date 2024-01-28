package es.ajgm.mynewapp.testbme.infrastructure.persistence.mappers;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import es.ajgm.mynewapp.testbme.infrastructure.persistence.model.ProductEntityModel;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  Product fromModel(ProductEntityModel productEntityModel);

  ProductEntityModel toModel(Product product);

  default Page<Product> fromModels(Page<ProductEntityModel> productPage) {

    final Page<Product> productsPage = productPage.map(this::fromModel);

    return productsPage;
  }

  default Optional<Product> fromOptionalModel(Optional<ProductEntityModel> optionalProductEntity) {
    return (optionalProductEntity.isEmpty()) ? Optional.empty() : Optional.of(fromModel(optionalProductEntity.get()));
  }
}
