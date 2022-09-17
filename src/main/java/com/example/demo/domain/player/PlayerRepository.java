package com.example.demo.domain.player;

import com.example.demo.domain.game.Game;
import com.example.demo.errors.PlayerNotFoundException;

public interface PlayerRepository {

    Player findById(Integer playerId) throws PlayerNotFoundException;
    Player saveNewPlayerToGame(Game game);
    void deleteById(Integer playerId);
}
