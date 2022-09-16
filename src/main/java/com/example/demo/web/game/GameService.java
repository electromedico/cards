package com.example.demo.web.game;

import com.example.demo.domain.player.Player;

import java.util.List;

public interface GameService {
    int newGame();

    void deleteGame(int id);

    void shuffle(int id);

    void addDeck(int id);

    void addPlayer(int id, int playerId);

    Player dealCardToPlayer(int id, int playerId);

    List<Player> getPlayers(int id);

    void removePlayer(int id, int playerId);
}
