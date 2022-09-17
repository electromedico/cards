/* (C) 2022 */
package com.example.demo.domain.player;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.game.GameRepository;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import com.example.demo.web.player.PlayerService;
import java.util.AbstractMap;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

  @Autowired PlayerRepository playerRepository;

  @Autowired GameRepository gameRepository;

  @Override
  public AbstractMap.SimpleEntry<Revision, Player> addPlayer(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    Player player = playerRepository.saveNewPlayerToGame(game);
    return new AbstractMap.SimpleEntry<>(game.getRevision(), player);
  }

  @Override
  public Revision removePlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException {
    Game game = gameRepository.findById(gameId);
    Player player = playerRepository.findById(playerId);
    game.getRevision().matchAndBump(revision);
    playerRepository.deleteById(player.id);
    return game.getRevision();
  }
}
