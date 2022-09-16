package com.example.demo.web.game;

import java.util.List;

public interface GameService {
    int newGame();

    void deleteGame(int id);

    void shuffle(int id);

    void addDeck(int id);

    void addPlayer(int id, int playerId);

    List<Integer> dealCardToPlayer(int id, int playerId);

    void getPlayers(int id);

    void removePlayer(int id, int playerId);
}
