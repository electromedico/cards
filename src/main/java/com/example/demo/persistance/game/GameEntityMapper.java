/* (C) 2022 */
package com.example.demo.persistance.game;

import com.example.demo.domain.game.Game;
import com.example.demo.utils.Revision;
import org.springframework.stereotype.Component;

@Component
public class GameEntityMapper {

  public Game fromDto(GameEntity gameEntity) {

    return Game.builder()
        .id(gameEntity.getId())
        .revision(new Revision(gameEntity.getRevision() != null ? gameEntity.getRevision() : 0))
        .build();
  }

  public GameEntity toDto(Game game) {
    GameEntity gameEntity = new GameEntity();
    gameEntity.setId(game.getId());
    gameEntity.setRevision(game.getRevision() != null ? game.getRevision().getValue() : 0);
    return gameEntity;
  }
}
