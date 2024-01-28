package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.out.ProductSavePort;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ProductCreationServiceTest {

  private static final long TEST_ID = 123;

  @Mock
  private ProductSavePort productRepositoryMock;

  private ProductCreationService productCreationService;


  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
    productCreationService = new ProductCreationService(productRepositoryMock);
  }


  @DisplayName("Should create a product")
  @SneakyThrows
  @Test
  void shouldCreateProduct() {

    Product testProduct = new Product();

    given(productRepositoryMock.save(testProduct)).willReturn(testProduct);

    Product productRetrieved = productCreationService.createProduct(testProduct);

    assertEquals(productRetrieved, testProduct);

  }
}
