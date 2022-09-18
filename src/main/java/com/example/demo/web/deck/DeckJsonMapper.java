/* (C) 2022 */
package com.example.demo.web.deck;

import com.example.demo.domain.deck.Deck;
import com.example.demo.web.card.CardJsonMapper;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeckJsonMapper {

  @Autowired CardJsonMapper cardJsonMapper;

  public DeckJson mapDeck(Deck deck) {

    return DeckJson.builder()
        .cards(deck.getCards().stream().map(cardJsonMapper::mapCard).toList())
        .build();
  }

  public CardsLeftJson mapCardsLeft(Deck deck) {
    AtomicInteger clubs = new AtomicInteger();
    AtomicInteger hearts = new AtomicInteger();
    AtomicInteger spades = new AtomicInteger();
    AtomicInteger diamonds = new AtomicInteger();
    deck.getCards()
        .forEach(
            card -> {
              switch (card.getSuit()) {
                case HEART -> hearts.incrementAndGet();
                case SPADE -> spades.incrementAndGet();
                case CLUB -> clubs.incrementAndGet();
                case DIAMOND -> diamonds.incrementAndGet();
              }
            });

    return CardsLeftJson.builder()
        .clubsCount(clubs.get())
        .diamondsCount(diamonds.get())
        .heartsCount(hearts.get())
        .spadesCount(spades.get())
        .build();
  }
}
