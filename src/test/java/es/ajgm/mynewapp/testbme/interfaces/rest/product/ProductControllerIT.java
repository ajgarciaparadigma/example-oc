package es.ajgm.mynewapp.testbme.interfaces.rest.product;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.ajgm.mynewapp.testbme.application.services.ProductCreationService;
import es.ajgm.mynewapp.testbme.application.services.ProductUpdateService;
import es.ajgm.mynewapp.testbme.application.services.ProductDeleteService;
import es.ajgm.mynewapp.testbme.application.services.ProductFindAllService;
import es.ajgm.mynewapp.testbme.application.services.ProductGetByIdService;

import es.ajgm.mynewapp.testbme.domain.exceptions.ProductNotFoundException;
import es.ajgm.mynewapp.testbme.domain.model.entities.Product;
import es.ajgm.mynewapp.testbme.domain.model.enums.ColorEnum;
import es.ajgm.mynewapp.testbme.interfaces.rest.product.mappers.ProductDTOMapperImpl;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ColorRequestEnum;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductRequest;
import es.ajgm.mynewapp.testbme.openapi.interfaces.rest.product.dtos.ProductUpdateRequest;

import com.paradigma.architecture.core.rest.exceptionhandling.GlobalExceptionHandler;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = ProductController.class)
@Import({GlobalExceptionHandler.class, ProductDTOMapperImpl.class})
class ProductControllerIT
{

  private static final long PRODUCT_ID = 123L;

  private static final String ID_PATTERN = "$.id";
  private static final String NAME_PATTERN = "$.name";
  private static final String PRICE_PATTERN = "$.price";
  private static final String DESCRIPTION_PATTERN = "$.description";
  private static final String EMAIL_PATTERN = "$.email";
  private static final String EXPEDITION_DATE_PATTERN = "$.expeditionDate";
  private static final String EXPIRATION_DATE_PATTERN = "$.expirationDate";
  private static final String COLOR_PATTERN = "$.color";

  private final OffsetDateTime offsetDateTime = OffsetDateTime.now();

  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductCreationService productCreationService;
  @MockBean
  private ProductUpdateService productUpdateService;
  @MockBean
  private ProductDeleteService productDeleteService;
  @MockBean
  private ProductFindAllService productFindAllService;
  @MockBean
  private ProductGetByIdService productGetByIdService;

  @Test
  @SneakyThrows
  @DisplayName("Should get a Product by resource id")
  void shouldGetProduct()
  {
    // Given
    final Product testProduct = obtainTestProduct();

    given(productGetByIdService.getProduct(testProduct.getId())).willReturn(testProduct);

    // When Then
    mockMvc.perform(get("/products/{id}", PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath(ID_PATTERN, is(testProduct.getId().intValue())))
      .andExpect(jsonPath(NAME_PATTERN, is(testProduct.getName())))
      .andExpect(jsonPath(DESCRIPTION_PATTERN, is(testProduct.getDescription())));

  }


  @Test
  @SneakyThrows
  @DisplayName("Should return a not found status code when the product to get doesn't exists")
  void shouldSendNotFoundWhenTheProductToGetDoesNotExists()
  {
    // Given
    given(productGetByIdService.getProduct(anyLong())).willThrow(new ProductNotFoundException(String.valueOf(PRODUCT_ID)));

    // When Then
    mockMvc.perform(get("/products/{id}", PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

  }


  @Test
  @SneakyThrows
  @DisplayName("Should get a collection of products paginated")
  void shouldGetProductCollection()
  {
    // Given
    final Product testProduct = obtainTestProduct();
    final List<Product> testProducts = List.of(testProduct, testProduct, testProduct);
    final Page<Product> testProductPage = new PageImpl<>(testProducts);

    given(productFindAllService.getProducts(anyInt(), anyInt())).willReturn(testProductPage);

    // When Then
    mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON).param("name", "test_name"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.products.[0].id", is(testProduct.getId().intValue())))
      .andExpect(jsonPath("$.products.[0].name", is(testProduct.getName())))
      .andExpect(jsonPath("$.products.[0].description", is(testProduct.getDescription())))

      .andExpect(jsonPath("$.pagination.retrievedResults", is(3)))
      .andExpect(jsonPath("$.pagination.totalElements", is(3)))
      .andExpect(jsonPath("$.pagination.requestedPage", is(0)))
      .andExpect(jsonPath("$.pagination.requestedSize", is(3)))
      .andExpect(jsonPath("$.pagination.nextPage", is("")))
      .andExpect(jsonPath("$.pagination.previousPage", is("")));

  }


  @Test
  @SneakyThrows
  @DisplayName("Should create a product")
  void shouldCreateProduct()
  {
    // Given
    final ProductRequest testProductRequest = getTestProductRequest();
    final Product testProduct = obtainTestProduct();

    given(productCreationService.createProduct(any(Product.class))).willReturn(testProduct);

    // When Then
    mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testProductRequest)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath(ID_PATTERN, is(testProduct.getId().intValue())))
      .andExpect(jsonPath(NAME_PATTERN, is(testProduct.getName())))
      .andExpect(jsonPath(DESCRIPTION_PATTERN, is(testProduct.getDescription())));

  }


  @Test
  @SneakyThrows
  @DisplayName("Should send a bad request status code when the there are not the minimum properties to create a product")
  void shouldSendBadRequestWhenThereAreNotTheMinimumPropertiesToCreateAProduct()
  {
    // Given
    final ProductUpdateRequest invalidProductRequest = ProductUpdateRequest.builder()
      .price(BigDecimal.ONE)
      .description("Test descripcion")
      .build();

    // When Then
    mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidProductRequest)))
      .andExpect(status().isBadRequest());

  }


  @Test
  @SneakyThrows
  @DisplayName("Should update a product")
  void shouldUpdateProduct()
  {
    // Given
    final ProductUpdateRequest testProductRequest = getTestProductUpdateRequest();
    final Product testProduct = obtainTestProduct();

    given(productUpdateService.updateProduct(any(Product.class))).willReturn(testProduct);

    // When Then
    mockMvc.perform(put("/products/" + PRODUCT_ID).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testProductRequest)))
      .andExpect(status().isOk())
      .andExpect(jsonPath(NAME_PATTERN, is(testProduct.getName())))
      .andExpect(jsonPath(PRICE_PATTERN, is(testProduct.getPrice().intValue())))
      .andExpect(jsonPath(DESCRIPTION_PATTERN, is(testProduct.getDescription())))
      .andExpect(jsonPath(EMAIL_PATTERN, is(testProduct.getEmail())))
      .andExpect(jsonPath(EXPEDITION_DATE_PATTERN, is(dateTimeFormatter.format(testProduct.getExpeditionDate()))))
      .andExpect(jsonPath(EXPIRATION_DATE_PATTERN, is(dateTimeFormatter.format(testProduct.getExpirationDate()))))
      .andExpect(jsonPath(COLOR_PATTERN, is(testProduct.getColor().getValue())))
      .andExpect(jsonPath(ID_PATTERN, is(testProduct.getId().intValue())));
  }


  @Test
  @SneakyThrows
  @DisplayName("Should send a Bad Request status when some property is not present in the update product payload")
  void shouldSendABadRequestWhenSomePropertyIsNotPresentInTheUpdateProductPayload()
  {
    // Given
    final ProductUpdateRequest invalidProductUpdateRequest = ProductUpdateRequest.builder()
      .price(BigDecimal.ONE)
      .description("Test descripcion")
      .build();

    // When Then
    mockMvc.perform(put("/products/" + PRODUCT_ID).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidProductUpdateRequest)))
      .andExpect(status().isBadRequest());

  }


  @Test
  @SneakyThrows
  @DisplayName("Should send a Not Found when the required product does not exists while updating")
  void shouldSendANotFoundWhenTheRequiredProductDoesNotExistsWhileUpdating()
  {
    // Given
    final Product testProduct = obtainTestProduct();
    final ProductUpdateRequest productUpdateRequest = getTestProductUpdateRequest();

    given(productUpdateService.updateProduct(any(Product.class))).willThrow(new ProductNotFoundException(String.valueOf(testProduct.getId())));

    mockMvc.perform(put("/products/" + PRODUCT_ID).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(productUpdateRequest)))
      .andExpect(status().isNotFound());

  }


  @Test
  @SneakyThrows
  @DisplayName("Should delete a product")
  void shouldDeleteProduct()
  {
    // When Then
    mockMvc.perform(delete("/products/" + PRODUCT_ID))
      .andExpect(status().isNoContent());

    then(productDeleteService).should().deleteProduct(PRODUCT_ID);

  }


  @Test
  @SneakyThrows
  @DisplayName("Should return a not found error code when the product to delete doesn't exists")
  void shouldSendNotFoundWhenTheProductToDeleteDoesNotExists()
  {
    // Given
    doThrow(new ProductNotFoundException(String.valueOf(PRODUCT_ID)))
      .when(productDeleteService)
      .deleteProduct(anyLong());

    // When Then
    mockMvc.perform(delete("/products/{id}", PRODUCT_ID)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());

  }

  private Product obtainTestProduct()
  {

    return Product.builder()
      .id(PRODUCT_ID)
      .name("test name")
      .price(BigDecimal.ONE)
      .description("Test description")
      .email("email@email.com")
      .expeditionDate(offsetDateTime)
      .expirationDate(offsetDateTime)
      .color(ColorEnum.BLACK)
      .build();

  }


  private ProductUpdateRequest getTestProductUpdateRequest()
  {

    return ProductUpdateRequest.builder()
      .name("test name")
      .price(BigDecimal.ONE)
      .description("Test description")
      .email("email@email.com")
      .expeditionDate(offsetDateTime)
      .expirationDate(offsetDateTime)
      .color(ColorRequestEnum.BLACK)
      .build();

  }


  private ProductRequest getTestProductRequest()
  {

    return ProductRequest.builder()
      .name("test name")
      .price(BigDecimal.ONE)
      .description("Test description")
      .build();

  }

}
