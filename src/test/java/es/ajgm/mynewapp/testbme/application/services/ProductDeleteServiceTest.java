package es.ajgm.mynewapp.testbme.application.services;

import es.ajgm.mynewapp.testbme.application.ports.out.ProductDeletePort;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
class ProductDeleteServiceTest {

  private static final long TEST_ID = 123;

  @Mock
  private ProductDeletePort productRepositoryMock;

  private ProductDeleteService productDeleteService;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
    productDeleteService = new ProductDeleteService(productRepositoryMock);
  }

  @DisplayName("Should delete a product")
  @SneakyThrows
  @Test
  void shouldDeleteProduct() {

    productDeleteService.deleteProduct(TEST_ID);

    then(productRepositoryMock).should().deleteById(TEST_ID);

  }

}
