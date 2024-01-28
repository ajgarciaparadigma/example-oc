package es.ajgm.mynewapp.testbme.support;

import es.ajgm.mynewapp.testbme.infrastructure.persistence.model.ProductEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestProductJpaRepository extends JpaRepository<ProductEntityModel, Long> {

}
