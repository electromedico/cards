/* (C) 2022 */
package com.example.demo.web.card;

import com.example.demo.domain.deck.Card;
import org.springframework.stereotype.Component;

@Component
public class CardJsonMapper {
  public CardJson mapCard(Card card) {
    return CardJson.builder().suit(card.getSuit()).value(card.getValue()).build();
  }
}
