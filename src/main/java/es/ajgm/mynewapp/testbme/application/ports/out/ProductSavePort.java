package es.ajgm.mynewapp.testbme.application.ports.out;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;

public interface ProductSavePort
{

  Product save(Product entity);

}
