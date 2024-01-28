package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.in.*;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductGetByIdPort;
import es.ajgm.mynewapp.testbme.domain.exceptions.ProductNotFoundException;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductGetByIdService implements ProductGetByIdUseCase {

  private final ProductGetByIdPort productRepository;

  @Override
  public Product getProduct(Long id) {

    return productRepository
      .findById(id)
      .orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));

  }

}
