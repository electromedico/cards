/* (C) 2022 */
package com.example.demo.web.deck;

import com.example.demo.web.card.CardJson;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeckJson {
  private List<CardJson> cards;
}
