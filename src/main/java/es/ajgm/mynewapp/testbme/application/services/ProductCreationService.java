package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.in.ProductCreateUseCase;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductSavePort;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductCreationService implements ProductCreateUseCase{

  private final ProductSavePort productRepository;

  @Override
  public Product createProduct(Product product) {

    return productRepository.save(product);

  }

}
