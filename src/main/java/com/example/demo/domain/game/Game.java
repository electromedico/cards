package com.example.demo.domain.game;

import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.player.Player;
import com.example.demo.utils.Revision;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Game {
    private Integer id;
    private Revision revision;
    private List<Player> players;
    private Deck deck;
}
