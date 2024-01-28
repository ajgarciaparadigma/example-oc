package es.ajgm.mynewapp.testbme.application.ports.in;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;

public interface ProductGetByIdUseCase {

  Product getProduct(Long id);

}
