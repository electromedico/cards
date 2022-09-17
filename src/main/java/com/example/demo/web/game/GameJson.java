package com.example.demo.web.game;

import com.example.demo.web.deck.DeckJson;
import com.example.demo.web.player.PlayerJson;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameJson {
    private Integer id;
    private List<PlayerJson> players;
    private DeckJson deck;
}
