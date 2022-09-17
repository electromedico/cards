/* (C) 2022 */
package com.example.demo.domain.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.player.Player;
import com.example.demo.domain.player.PlayerRepository;
import com.example.demo.errors.EmptyDeckException;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.utils.Revision;
import com.example.demo.web.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GameServiceImpl implements GameService {

  @Autowired private GameRepository gameRepository;

  @Autowired private PlayerRepository playerRepository;

  @Override
  public Integer newGame() {
    Game game = Game.builder().deck(Deck.buildNewDeck()).build();
    return gameRepository.save(game).getId();
  }

  @Override
  public void deleteGame(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().match(revision);
    gameRepository.deleteById(game.getId());
    playerRepository.deleteByGameId(gameId);
  }

  @Override
  public Revision addDeck(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    List<Card> cards = new ArrayList<>(game.getDeck().getCards());
    cards.addAll(Deck.buildNewSetOfCards());
    game.getDeck().setCards(cards);
    gameRepository.update(game);
    return game.getRevision();
  }

  @Override
  public Game dealCardToPlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException,
          EmptyDeckException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);

    List<Player> players = playerRepository.findByGameID(gameId);
    game.setPlayers(players);
    Player player =
        players.stream()
            .filter(p -> Objects.equals(p.getId(), playerId))
            .findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));
    List<Card> newDeck = new ArrayList<>(game.getDeck().getCards());
    if (newDeck.isEmpty()) {
      throw new EmptyDeckException();
    }
    Card card = newDeck.remove(0);
    game.getDeck().setCards(newDeck);
    List<Card> newPlayerCards =
        CollectionUtils.isEmpty(player.getCards())
            ? new ArrayList<>()
            : new ArrayList<>(player.getCards());
    newPlayerCards.add(card);
    player.setCards(newPlayerCards);
    playerRepository.updatePlayer(player, gameId);
    gameRepository.update(game);
    return game;
  }

  @Override
  public Revision shuffle(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    game.getDeck().shuffle();
    gameRepository.update(game);
    return game.getRevision();
  }
}
