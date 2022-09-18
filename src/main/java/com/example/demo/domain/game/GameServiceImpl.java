/* (C) 2022 */
package com.example.demo.domain.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.deck.DeckRepository;
import com.example.demo.domain.player.Player;
import com.example.demo.domain.player.PlayerRepository;
import com.example.demo.errors.*;
import com.example.demo.utils.Revision;
import com.example.demo.web.game.GameService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GameServiceImpl implements GameService {

  @Autowired private GameRepository gameRepository;

  @Autowired private PlayerRepository playerRepository;

  @Autowired private DeckRepository deckRepository;

  @Override
  public Integer newGame() {
    Game game = Game.builder().build();
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
      throws GameNotFoundFoundException, RevisionsDontMatchException, NoUnassignedDecksException {
    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);
    Deck deck = deckRepository.findFirstUnassigned();
    deck.setGameId(game.getId());
    deckRepository.updateDeck(deck);
    gameRepository.update(game);
    return game.getRevision();
  }

  @Override
  public Game dealCardToPlayer(Integer gameId, Integer playerId, Revision revision)
      throws RevisionsDontMatchException, GameNotFoundFoundException, PlayerNotFoundException,
          EmptyDeckException {

    Game game = gameRepository.findById(gameId);
    game.getRevision().matchAndBump(revision);

    List<Deck> decks = deckRepository.findDecksByGameId(gameId);
    game.setDecks(decks);

    List<Player> players = playerRepository.findByGameID(gameId);
    game.setPlayers(players);

    Player player =
        players.stream()
            .filter(p -> Objects.equals(p.getId(), playerId))
            .findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));

    Deck newDeck =
        decks.stream()
            .filter(deck -> !deck.getCards().isEmpty())
            .findFirst()
            .orElseThrow(EmptyDeckException::new);

    List<Card> newCards = new ArrayList<>(newDeck.getCards());
    Card card = newCards.remove(0);
    game.getDecks().stream()
        .filter(deck -> Objects.equals(deck.getId(), newDeck.getId()))
        .findFirst()
        .get()
        .setCards(newCards);

    List<Card> newPlayerCards =
        CollectionUtils.isEmpty(player.getCards())
            ? new ArrayList<>()
            : new ArrayList<>(player.getCards());
    newPlayerCards.add(card);
    player.setCards(newPlayerCards);
    playerRepository.updatePlayer(player, gameId);
    deckRepository.updateDeck(newDeck);
    gameRepository.update(game);
    return game;
  }

  @Override
  public Revision shuffle(Integer gameId, Revision revision)
      throws GameNotFoundFoundException, RevisionsDontMatchException {
    Game game = gameRepository.findById(gameId);
    List<Deck> decks = deckRepository.findDecksByGameId(gameId);
    game.setDecks(decks);
    game.getRevision().matchAndBump(revision);
    game.getDecks().forEach(Deck::shuffle);
    deckRepository.updateDecks(decks);
    gameRepository.update(game);
    return game.getRevision();
  }
}
