package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.in.*;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductDeleteService implements ProductDeleteUseCase {

  private final ProductDeletePort productRepository;

  @Override
  public void deleteProduct(Long id) {

    productRepository.deleteById(id);

  }

}
