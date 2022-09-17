/* (C) 2022 */
package com.example.demo.persistance.game;

import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.game.Game;
import com.example.demo.persistance.cards.CardEntityMapper;
import com.example.demo.utils.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Clob;

@Component
public class GameEntityMapper {

  @Autowired private CardEntityMapper cardEntityMapper;

  public Game fromDto(GameEntity gameEntity) {

    Deck deck = Deck.builder().cards(cardEntityMapper.mapCards(gameEntity.getCards())).build();

    return Game.builder()
        .id(gameEntity.getId())
        .deck(deck)
        .revision(new Revision(gameEntity.getRevision() != null ? gameEntity.getRevision() : 0))
        .build();
  }

  public GameEntity toDto(Game game) {
    GameEntity gameEntity = new GameEntity();
    gameEntity.setId(game.getId());
    Clob cards;
    cards = cardEntityMapper.mapCards(game.getDeck().getCards());
    gameEntity.setCards(cards);
    gameEntity.setRevision(game.getRevision() != null ? game.getRevision().getValue() : 0);
    return gameEntity;
  }
}
