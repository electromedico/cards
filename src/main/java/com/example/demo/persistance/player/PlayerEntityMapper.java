/* (C) 2022 */
package com.example.demo.persistance.player;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.player.Player;
import com.example.demo.persistance.cards.CardEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerEntityMapper {

  @Autowired private CardEntityMapper cardEntityMapper;

  public Player fromDto(PlayerEntity playerEntity) {
    List<Card> cards;
    cards = cardEntityMapper.mapCards(playerEntity.getCards());
    return Player.builder().id(playerEntity.getId()).cards(cards).build();
  }

  public PlayerEntity toDto(Player player, Integer gameId) {
    PlayerEntity playerEntity = new PlayerEntity();
    playerEntity.setId(player.getId());
    playerEntity.setGameId(gameId);
    playerEntity.setCards(cardEntityMapper.mapCards(player.getCards()));
    return playerEntity;
  }
}
