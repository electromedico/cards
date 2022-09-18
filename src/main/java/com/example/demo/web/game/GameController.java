/* (C) 2022 */
package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.*;
import com.example.demo.utils.Revision;
import com.example.demo.web.player.PlayerService;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
  @Autowired public GameService gameService;

  @Autowired public PlayerService playerService;

  @Autowired public GameJsonMapper gameJsonMapper;

  @GetMapping("/new")
  public Integer newGame(HttpServletResponse response) {
    response.addHeader(HttpHeaders.IF_MATCH, "0");
    return gameService.newGame();
  }

  @DeleteMapping("/{id}")
  public void deleteGame(@PathVariable int id, @RequestHeader(HttpHeaders.IF_MATCH) int revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    gameService.deleteGame(id, new Revision(revision));
  }

  @GetMapping("/{id}/add-deck")
  public void addDeck(
      @PathVariable int id,
      @RequestHeader(HttpHeaders.IF_MATCH) int revision,
      HttpServletResponse response)
      throws RevisionsDontMatchException, GameNotFoundFoundException, NoUnassignedDecksException {
    Revision newRevision = gameService.addDeck(id, new Revision(revision));
    response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());
  }

  @GetMapping("/{id}/add-player")
  public Integer addPlayer(
      @PathVariable int id,
      @RequestHeader(HttpHeaders.IF_MATCH) int revision,
      HttpServletResponse response)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    AbstractMap.SimpleEntry<Revision, Player> revisionPlayer =
        playerService.addPlayer(id, new Revision(revision));
    response.addHeader(HttpHeaders.IF_MATCH, revisionPlayer.getKey().toString());
    return revisionPlayer.getValue().getId();
  }

  @GetMapping("/{id}/remove-player/{playerId}")
  public void removePlayer(
      @PathVariable int id,
      @PathVariable int playerId,
      @RequestHeader(HttpHeaders.IF_MATCH) int revision,
      HttpServletResponse response)
      throws RevisionsDontMatchException, PlayerNotFoundException, GameNotFoundFoundException {
    Revision newRevision = playerService.removePlayer(id, playerId, new Revision(revision));
    response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());
  }

  @GetMapping("/{id}/deal-to-player/{playerId}")
  public GameJson dealCardToPlayer(
      @PathVariable int id,
      @PathVariable int playerId,
      @RequestHeader(HttpHeaders.IF_MATCH) int revision,
      HttpServletResponse response)
      throws RevisionsDontMatchException, PlayerNotFoundException, GameNotFoundFoundException,
          EmptyDeckException {
    Game game = gameService.dealCardToPlayer(id, playerId, new Revision(revision));
    response.addHeader(HttpHeaders.IF_MATCH, game.getRevision().toString());
    return gameJsonMapper.map(game);
  }

  @GetMapping("/{id}/get-players-with-totals")
  public List<PlayerWithTotalJson> getPlayersWithTotals(@PathVariable int id)
      throws GameNotFoundFoundException {
    return playerService.getPlayersByGameId(id).stream()
        .map(gameJsonMapper::mapPlayerTotals)
        .sorted(Comparator.comparingInt(PlayerWithTotalJson::getTotal))
        .toList();
  }

  @GetMapping("/{id}/shuffle")
  public void shuffle(
      @PathVariable int id,
      @RequestHeader(HttpHeaders.IF_MATCH) int revision,
      HttpServletResponse response)
      throws RevisionsDontMatchException, GameNotFoundFoundException {
    Revision newRevision = gameService.shuffle(id, new Revision(revision));
    response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());
  }
}
