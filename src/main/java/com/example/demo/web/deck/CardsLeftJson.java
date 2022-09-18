/* (C) 2022 */
package com.example.demo.web.deck;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardsLeftJson {
  private Integer heartsCount;
  private Integer spadesCount;
  private Integer clubsCount;
  private Integer diamondsCount;
}
