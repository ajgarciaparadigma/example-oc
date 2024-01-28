package es.ajgm.mynewapp.testbme.interfaces.rest.product.mappers;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.Pagination;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductRequest;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductResponse;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductUpdateRequest;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class ProductDTOMapper {

  private static final ProductDTOMapper INSTANCE = Mappers.getMapper(ProductDTOMapper.class);

  @Mapping(target = "id", ignore = true)
  public abstract Product from(ProductRequest productRequest);

  @Mapping(target = "id", ignore = true)
  public abstract Product from(ProductUpdateRequest productUpdateRequest);
  public abstract ProductResponse to(Product product);

  public abstract ProductResponse toProductResourceResponse(Product product);

  public ProductsResponse mapProductPageToProductsResponse(Page<Product> products) {

    final Pagination pagination = new Pagination(
      products.getNumber(),
      products.getSize(),
      products.getNumberOfElements(),
      products.getTotalElements()
      ,"","");

    return ProductsResponse.builder()
      .products(products.map(INSTANCE::to).getContent())
      .pagination(pagination)
      .build();

  }

  public Product fromProductUpdateRequestAndId(ProductUpdateRequest productUpdateRequest, Long id) {

    final Product product = from(productUpdateRequest);
    product.setId(id);

    return product;

  }
}
