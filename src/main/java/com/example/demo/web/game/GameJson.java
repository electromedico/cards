/* (C) 2022 */
package com.example.demo.web.game;

import com.example.demo.web.deck.DeckJson;
import com.example.demo.web.player.PlayerJson;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameJson {
  private Integer id;
  private List<PlayerJson> players;
  private List<DeckJson> decks;
}
