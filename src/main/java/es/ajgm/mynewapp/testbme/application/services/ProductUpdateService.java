package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.in.*;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductGetByIdPort;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductSavePort;
import es.ajgm.mynewapp.testbme.domain.exceptions.ProductNotFoundException;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductUpdateService implements ProductUpdateUseCase {

  private final ProductSavePort productSavePort;

  private final ProductGetByIdPort productGetByIdPort;

  @Override
  public Product updateProduct(Product product) {

    Long id = product.getId();

    if (productGetByIdPort.findById(id).isPresent()) {
      return productSavePort.save(product);
    }

    throw new ProductNotFoundException(String.valueOf(id));

  }


}
