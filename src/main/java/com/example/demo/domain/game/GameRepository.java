package com.example.demo.domain.game;

import com.example.demo.persistance.game.GameEntity;

public interface GameRepository {

    int createGame();

    Boolean exists(int id);

    void deleteGame(int id);

    GameEntity getGameById(int id);

    Integer save(GameEntity game);
}
