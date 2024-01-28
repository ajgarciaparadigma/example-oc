package es.ajgm.mynewapp.testbme.domain.model.entities;

import es.ajgm.mynewapp.testbme.domain.model.enums.ColorEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private String email;
  private OffsetDateTime expeditionDate;
  private OffsetDateTime expirationDate;
  private ColorEnum color;

}
