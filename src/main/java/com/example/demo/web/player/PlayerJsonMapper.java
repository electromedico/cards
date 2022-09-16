package com.example.demo.web.player;

import com.example.demo.domain.player.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerJsonMapper {

    public PlayerJson mapPlayer(Player player) {
        return new PlayerJson.PlayerJsonBuilder().id(player.getId()).cards(player.getCards()).build();
    }
}
