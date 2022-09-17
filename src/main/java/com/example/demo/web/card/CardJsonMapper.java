/* (C) 2022 */
package com.example.demo.web.card;

import com.example.demo.domain.deck.Card;
import org.springframework.stereotype.Component;

@Component
public class CardJsonMapper {
  public CardJson mapCard(Card card) {
    return CardJson.builder().suit(card.getSuit()).value(mapValue(card.getValue())).build();
  }

  private String mapValue(Integer value) {
    return switch (value) {
      case 1 -> "A";
      case 11 -> "J";
      case 12 -> "Q";
      case 13 -> "K";
      default -> String.valueOf(value);
    };
  }
}
