/* (C) 2022 */
package com.example.demo.domain.game;

import com.example.demo.errors.GameNotFoundFoundException;

public interface GameRepository {

  Game save(Game game);

  void update(Game game);

  Game findById(Integer gameId) throws GameNotFoundFoundException;

  void deleteById(Integer gameId);
}
