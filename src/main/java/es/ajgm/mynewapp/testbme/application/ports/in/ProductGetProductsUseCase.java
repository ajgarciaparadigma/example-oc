package es.ajgm.mynewapp.testbme.application.ports.in;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductGetProductsUseCase {

  Page<Product> getProducts(Integer pageNumber, Integer pageSize);


}
