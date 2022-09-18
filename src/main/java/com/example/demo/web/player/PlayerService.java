/* (C) 2022 */
package com.example.demo.web.player;

import com.example.demo.domain.player.Player;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import java.util.AbstractMap;
import java.util.List;

public interface PlayerService {

  AbstractMap.SimpleEntry<Revision, Player> addPlayer(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException;

  Revision removePlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException;

  List<Player> getPlayersByGameId(Integer gameId) throws GameNotFoundFoundException;

  Player getPlayerById(Integer playerId) throws PlayerNotFoundException;
}
