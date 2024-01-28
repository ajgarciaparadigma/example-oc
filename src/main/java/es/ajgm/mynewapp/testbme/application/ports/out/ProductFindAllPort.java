package es.ajgm.mynewapp.testbme.application.ports.out;

import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductFindAllPort
{
  Page<Product> findAll(Integer pageNumber, Integer pageSize);

}
