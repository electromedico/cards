/* (C) 2022 */
package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.EmptyDeckException;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import java.util.List;

public interface GameService {
  Integer newGame();

  void deleteGame(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;

  Revision addDeck(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;

  List<Player> getPlayers(Integer gameId) throws GameNotFoundFoundException;

  Game dealCardToPlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException,
          EmptyDeckException;

  Revision removePlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException;

  Revision shuffle(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;
}
