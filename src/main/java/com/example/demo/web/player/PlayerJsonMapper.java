/* (C) 2022 */
package com.example.demo.web.player;

import com.example.demo.domain.player.Player;
import com.example.demo.web.card.CardJson;
import com.example.demo.web.card.CardJsonMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerJsonMapper {

  @Autowired CardJsonMapper cardJsonMapper;

  public PlayerJson mapPlayer(Player player) {
    List<CardJson> cards =
        Optional.of(player.getCards()).orElse(Collections.emptyList()).stream()
            .map(cardJsonMapper::mapCard)
            .toList();
    return new PlayerJson.PlayerJsonBuilder().id(player.getId()).cards(cards).build();
  }
}
