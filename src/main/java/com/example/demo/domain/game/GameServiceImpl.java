/* (C) 2022 */
package com.example.demo.domain.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.EmptyDeckException;
import com.example.demo.errors.GameNotFoundFoundException;
import com.example.demo.errors.PlayerNotFoundException;
import com.example.demo.errors.RevisionsDontMatchException;
import com.example.demo.persistance.game.GameEntityMapper;
import com.example.demo.utils.Revision;
import com.example.demo.web.game.GameService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  @Autowired private GameRepository gameRepository;

  @Autowired private GameEntityMapper gameEntityMapper;

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
  }

  @Override
  public Revision addDeck(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    game.getDeck().getCards().addAll(Deck.buildNewSetOfCards());
    gameRepository.save(game);
    return game.getRevision();
  }

  @Override
  public List<Player> getPlayers(Integer gameId) throws GameNotFoundFoundException {
    Game game = gameRepository.findById(gameId);
    return game.getPlayers();
  }

  @Override
  public Game dealCardToPlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException,
          EmptyDeckException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    Player player =
        game.getPlayers().stream()
            .filter(p -> p.getId() == playerId)
            .findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));
    List<Card> cards = game.getDeck().getCards();
    if (cards.isEmpty()) {
      throw new EmptyDeckException();
    }
    Card card = cards.remove(0);
    player.getCards().add(card);
    game = gameRepository.save(game);
    return game;
  }

  @Override
  public Revision removePlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    Player player =
        game.getPlayers().stream()
            .filter(element -> Objects.equals(element.getId(), playerId))
            .findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));
    game.getPlayers().remove(player);
    gameRepository.save(game);
    return game.getRevision();
  }

  @Override
  public Revision shuffle(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    game.getDeck().shuffle();
    gameRepository.save(game);
    return game.getRevision();
  }
}
