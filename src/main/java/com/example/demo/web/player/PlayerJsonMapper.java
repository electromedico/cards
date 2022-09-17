package com.example.demo.web.player;

import com.example.demo.domain.player.Player;
import com.example.demo.web.card.CardJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerJsonMapper {

    @Autowired
    CardJsonMapper cardJsonMapper;
    public PlayerJson mapPlayer(Player player) {
        return new PlayerJson.PlayerJsonBuilder()
                .id(player.getId())
                .cards(player.getCards().stream().map(cardJsonMapper::mapCard).toList())
                .build();
    }
}
