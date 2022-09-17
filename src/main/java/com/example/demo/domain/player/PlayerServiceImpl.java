/* (C) 2022 */
package com.example.demo.domain.player;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.game.GameRepository;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import com.example.demo.web.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    gameRepository.update(game);
    Player player = playerRepository.saveNewPlayerToGame(game);
    return new AbstractMap.SimpleEntry<>(game.getRevision(), player);
  }

  @Override
  public Revision removePlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);

    List<Player> players = playerRepository.findByGameID(gameId);
    game.setPlayers(players);
    List<Player> newPlayers = new ArrayList<>(players);
    Player player =
            newPlayers.stream()
                    .filter(element -> Objects.equals(element.getId(), playerId))
                    .findFirst()
                    .orElseThrow(() -> new PlayerNotFoundException(playerId));

    newPlayers.remove(player);
    game.setPlayers(newPlayers);
    // todo verify
    playerRepository.deleteById(playerId);
    gameRepository.update(game);
    return game.getRevision();
  }

  @Override
  public List<Player> getPlayersByGameId(Integer gameId) {
    return playerRepository.findByGameID(gameId);
  }

  @Override
  public Player getPlayerById(Integer playerId) throws PlayerNotFoundException {
    return playerRepository.findById(playerId);
  }
}
