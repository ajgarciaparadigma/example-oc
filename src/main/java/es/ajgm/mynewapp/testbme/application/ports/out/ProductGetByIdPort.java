package es.ajgm.mynewapp.testbme.application.ports.out;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;

import java.util.Optional;

public interface ProductGetByIdPort
{

  Optional<Product> findById(Long id);

}
