/* (C) 2022 */
package com.example.demo.persistance.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.game.GameRepository;
import com.example.demo.errors.GameNotFoundFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultGameRepository implements GameRepository {

  @Autowired private GameEntityDao gameEntityDao;
  @Autowired private GameEntityMapper gameEntityMapper;

  @Override
  public Game save(Game game) {
    GameEntity gameEntity = gameEntityDao.save(gameEntityMapper.toDto(game));
    return gameEntityMapper.fromDto(gameEntity);
  }

  // todo fix wierd bug and remove this
  @Override
  public void update(Game game) {
    gameEntityDao.save(gameEntityMapper.toDto(game));
  }

  @Override
  public Game findById(Integer gameId) throws GameNotFoundFoundException {
    return gameEntityDao
        .findById(gameId)
        .map(gameEntityMapper::fromDto)
        .orElseThrow(() -> new GameNotFoundFoundException(gameId));
  }

  @Override
  public void deleteById(Integer gameId) {
    gameEntityDao.deleteById(gameId);
  }
}
