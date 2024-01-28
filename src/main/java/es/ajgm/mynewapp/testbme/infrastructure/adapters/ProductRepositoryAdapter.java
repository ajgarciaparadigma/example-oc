package es.ajgm.mynewapp.testbme.infrastructure.adapters;

import es.ajgm.mynewapp.testbme.domain.exceptions.ProductNotFoundException;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import es.ajgm.mynewapp.testbme.infrastructure.persistence.ProductJpaRepository;
import es.ajgm.mynewapp.testbme.infrastructure.persistence.model.ProductEntityModel;
import es.ajgm.mynewapp.testbme.infrastructure.persistence.mappers.ProductMapper;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductDeletePort;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductFindAllPort;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductGetByIdPort;
import es.ajgm.mynewapp.testbme.application.ports.out.ProductSavePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductSavePort, ProductFindAllPort, ProductGetByIdPort, ProductDeletePort {

  private final ProductJpaRepository repository;
  private final ProductMapper mapper;

  @Override
  public Optional<Product> findById(Long id) {

    final Optional<ProductEntityModel> productMo = repository.findById(id);

    return mapper.fromOptionalModel(productMo);

  }

  @Override
  public Page<Product> findAll(Integer pageNumber, Integer pageSize) {

    final Pageable pageRequest =  PageRequest.of(pageNumber, pageSize);
    final Page<ProductEntityModel> productsMOs = repository.findAll(pageRequest);
    return mapper.fromModels(productsMOs);

  }

  @Override
  public Product save(Product product) {

    final ProductEntityModel productModel = mapper.toModel(product);
    final ProductEntityModel productSaved = repository.save(productModel);

    return mapper.fromModel(productSaved);

  }

  @Override
  public void deleteById(Long id) {

    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ProductNotFoundException(String.valueOf(id));
    }

  }

}
