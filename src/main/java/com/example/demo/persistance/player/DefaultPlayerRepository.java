/* (C) 2022 */
package com.example.demo.persistance.player;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.domain.player.PlayerRepository;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.persistance.game.GameEntity;
import com.example.demo.persistance.game.GameEntityMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultPlayerRepository implements PlayerRepository {
  @Autowired PlayerEntityDao playerEntityDao;
  @Autowired PlayerEntityMapper playerEntityMapper;

  @Autowired GameEntityMapper gameEntityMapper;

  @Override
  public void updatePlayer(Player player, Integer gameId) {
    playerEntityDao.save(playerEntityMapper.toDto(player, gameId));
  }

  @Override
  public Player findById(Integer playerId) throws PlayerNotFoundException {
    return playerEntityDao
        .findById(playerId)
        .map(playerEntityMapper::fromDto)
        .orElseThrow(() -> new PlayerNotFoundException(playerId));
  }

  @Override
  public List<Player> findByGameID(Integer gameId) {
    return playerEntityDao.findByGameId(gameId).stream().map(playerEntityMapper::fromDto).toList();
  }

  @Override
  public Player saveNewPlayerToGame(Game game) {
    GameEntity gameEntity = gameEntityMapper.toDto(game);
    PlayerEntity playerEntity = new PlayerEntity();
    playerEntity.setGameId(gameEntity.getId());
    return playerEntityMapper.fromDto(playerEntityDao.save(playerEntity));
  }

  @Override
  public void deleteById(Integer playerId) {
    playerEntityDao.deleteById(playerId);
  }

  @Override
  public void deleteByGameId(Integer gameId) {
    playerEntityDao.deleteByGameId(gameId);
  }
}
