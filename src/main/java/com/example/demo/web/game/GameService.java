/* (C) 2022 */
package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.errors.*;
import com.example.demo.utils.Revision;

public interface GameService {
  Integer newGame();

  void deleteGame(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;

  Revision addDeck(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException, NoUnassignedDecksException;

  Game dealCardToPlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException,
          EmptyDeckException;

  Revision shuffle(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;
}
