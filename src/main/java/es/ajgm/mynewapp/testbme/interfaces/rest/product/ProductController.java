package es.ajgm.mynewapp.testbme.interfaces.rest.product;

import com.paradigma.architecture.core.rest.configuration.HttpHeaderUtils;
import es.ajgm.mynewapp.testbme.application.ports.in.*;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import es.ajgm.mynewapp.testbme.interfaces.rest.product.mappers.ProductDTOMapper;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.ProductsApi;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductRequest;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductResponse;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductUpdateRequest;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

  private final ProductCreateUseCase productCreateUseCase;
  private final ProductDeleteUseCase productDeleteUseCase;
  private final ProductGetProductsUseCase productGetProdcutsUseCase;
  private final ProductGetByIdUseCase productGetByIdUseCase;
  private final ProductUpdateUseCase productUpdateUseCase;
  private final ProductDTOMapper productDTOMapper;

  @Override
  public ResponseEntity<ProductResponse> obtainProduct(Long id) {

    final Product product = productGetByIdUseCase.getProduct(id);

    return ResponseEntity.ok(productDTOMapper.toProductResourceResponse(product));

  }

  @Override
  public ResponseEntity<ProductsResponse> obtainPagedProducts(Integer firstPage, Integer pageSize) {

    final Page<Product> products = productGetProdcutsUseCase.getProducts(firstPage, pageSize);

    return ResponseEntity.ok(productDTOMapper.mapProductPageToProductsResponse(products));

  }

  @Override
  public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {

    final Product product = productDTOMapper.from(productRequest);

    final Product productSaved = productCreateUseCase.createProduct(product);

    return ResponseEntity.created(HttpHeaderUtils.toUri(productSaved.getId())).body(productDTOMapper.toProductResourceResponse(productSaved));

  }

  @Override
  public ResponseEntity<ProductResponse> updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {

    final Product productUpdate = productDTOMapper.fromProductUpdateRequestAndId(productUpdateRequest, id);

    final Product productUpdated = productUpdateUseCase.updateProduct(productUpdate);

    return ResponseEntity.ok(productDTOMapper.toProductResourceResponse(productUpdated));

  }

  @Override
  public ResponseEntity<Void> deleteProduct(Long id) {

    productDeleteUseCase.deleteProduct(id);
    return ResponseEntity.noContent().build();

  }
}
