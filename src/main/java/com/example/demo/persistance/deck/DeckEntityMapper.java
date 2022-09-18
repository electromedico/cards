/* (C) 2022 */
package com.example.demo.persistance.deck;

import com.example.demo.domain.deck.Deck;
import com.example.demo.persistance.cards.CardEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeckEntityMapper {

  @Autowired CardEntityMapper cardEntityMapper;

  public Deck fromDto(DeckEntity deckEntity) {

    return Deck.builder()
        .id(deckEntity.getId())
        .gameId(deckEntity.getGameId())
        .cards(cardEntityMapper.mapCards(deckEntity.getCards()))
        .build();
  }

  public DeckEntity toDto(Deck deck) {
    DeckEntity deckEntity = new DeckEntity();
    deckEntity.setId(deck.getId());
    deckEntity.setGameId(deck.getGameId());
    deckEntity.setCards(cardEntityMapper.mapCards(deck.getCards()));
    return deckEntity;
  }
}
