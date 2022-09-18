/* (C) 2022 */
package com.example.demo.web.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.web.deck.DeckJsonMapper;
import com.example.demo.web.player.PlayerJsonMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameJsonMapper {
  @Autowired private PlayerJsonMapper playerJsonMapper;
  @Autowired private DeckJsonMapper deckJsonMapper;

  public GameJson map(Game game) {
    List<Deck> decks = Optional.of(game.getDecks()).orElse(Collections.emptyList());
    return GameJson.builder()
        .id(game.getId())
        .players(game.getPlayers().stream().map(playerJsonMapper::mapPlayer).toList())
        .decks(decks.stream().map(deckJsonMapper::mapDeck).toList())
        .build();
  }

  public PlayerWithTotalJson mapPlayerTotals(Player player) {
    return PlayerWithTotalJson.builder()
        .id(player.getId())
        .total(player.getCards().stream().map(Card::getValue).reduce(0, Integer::sum))
        .build();
  }
}
