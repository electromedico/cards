package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.erros.GameNotFoundException;
import com.example.demo.erros.PlayerNotFoundException;
import com.example.demo.erros.RevisionsDontMatch;
import com.example.demo.utils.Revision;

import java.util.List;

public interface GameService {
    Integer newGame();

    void deleteGame(int gameId,Revision revision) throws GameNotFoundException, RevisionsDontMatch;

    Revision addDeck(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch;

    Game addPlayer(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch;

    Game dealCardToPlayer(int gameId, int playerId, Revision revision) throws RevisionsDontMatch, GameNotFoundException, PlayerNotFoundException;

    List<Player> getPlayers(int gameId) throws GameNotFoundException;

    Revision removePlayer(int gameId, int playerId,Revision revision) throws RevisionsDontMatch, GameNotFoundException, PlayerNotFoundException;

    Revision shuffle(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch;


}
