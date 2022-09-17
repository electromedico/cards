/* (C) 2022 */
package com.example.demo.domain.player;

import com.example.demo.domain.game.Game;
import com.example.demo.errors.PlayerNotFoundException;
import java.util.List;

public interface PlayerRepository {
  void updatePlayer(Player player, Integer gameId);

  Player findById(Integer playerId) throws PlayerNotFoundException;

  List<Player> findByGameID(Integer gameId);

  Player saveNewPlayerToGame(Game game);

  void deleteById(Integer playerId);

  void deleteByGameId(Integer gameId);
}
