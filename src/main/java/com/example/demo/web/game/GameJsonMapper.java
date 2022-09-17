package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.web.deck.DeckJsonMapper;
import com.example.demo.web.player.PlayerJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameJsonMapper {
    @Autowired
    private PlayerJsonMapper playerJsonMapper;
    @Autowired
    private DeckJsonMapper deckJsonMapper;

    public GameJson map(Game game){
        return GameJson.builder()
                .id(game.getId())
                .players(game.getPlayers().stream().map(playerJsonMapper::mapPlayer).toList())
                .deck(deckJsonMapper.map(game.getDeck()))
                .build();

    }
}
