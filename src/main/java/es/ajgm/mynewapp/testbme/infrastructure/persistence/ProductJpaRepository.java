package es.ajgm.mynewapp.testbme.infrastructure.persistence;

import es.ajgm.mynewapp.testbme.infrastructure.persistence.model.ProductEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntityModel, Long> { }
