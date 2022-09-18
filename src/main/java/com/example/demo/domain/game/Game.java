/* (C) 2022 */
package com.example.demo.domain.game;

import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.player.Player;
import com.example.demo.utils.Revision;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {
  private Integer id;
  private Revision revision;
  private List<Player> players;
  private List<Deck> decks;
}
