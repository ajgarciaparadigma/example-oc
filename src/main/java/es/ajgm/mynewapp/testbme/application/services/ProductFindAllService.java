package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.in.*;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductFindAllPort;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductFindAllService implements ProductGetProductsUseCase {

  private final ProductFindAllPort productRepository;

  @Override
  public Page<Product> getProducts(Integer pageNumber, Integer pageSize) {

    return productRepository.findAll(pageNumber, pageSize);
  }

}
