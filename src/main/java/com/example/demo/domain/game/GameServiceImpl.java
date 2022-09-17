package com.example.demo.domain.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.player.Player;
import com.example.demo.erros.GameNotFoundException;
import com.example.demo.erros.PlayerNotFoundException;
import com.example.demo.erros.RevisionsDontMatch;
import com.example.demo.persistance.game.GameEntityMapper;
import com.example.demo.utils.Revision;
import com.example.demo.web.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameEntityMapper gameEntityMapper;

    @Override
    public Integer newGame() {
        Game game = Game.builder().deck(Deck.buildNewDeck()).build();
        return gameRepository.save(gameEntityMapper.map(game));
    }

    @Override
    public void deleteGame(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().match(revision);
        gameRepository.deleteGame(gameId);
    }

    @Override
    public Revision addDeck(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().matchAndBump(revision);
        game.getDeck().getCards().addAll(Deck.buildNewSetOfCards());
        gameRepository.save(gameEntityMapper.map(game));
        return game.getRevision();
    }


    @Override
    public Game addPlayer(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().matchAndBump(revision);
        game.setPlayers(List.of(Player.builder().build()));
        gameRepository.save(gameEntityMapper.map(game));
        return game;
    }

    @Override
    public Game dealCardToPlayer(int gameId, int playerId, Revision revision) throws RevisionsDontMatch, GameNotFoundException, PlayerNotFoundException {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().matchAndBump(revision);
        Player player = game.getPlayers().stream().filter(element -> element.getId() == playerId).findFirst().orElseThrow(() -> new PlayerNotFoundException(playerId));
        Optional<Card> card = game.getDeck().getCards().stream().findAny();
        card.ifPresent(element -> player.getCards().add(element));
        gameRepository.save(gameEntityMapper.map(game));
        return game;

    }

    @Override
    public List<Player> getPlayers(int gameId) throws GameNotFoundException {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        return game.getPlayers();
    }

    @Override
    public Revision removePlayer(int gameId, int playerId, Revision revision) throws RevisionsDontMatch, GameNotFoundException, PlayerNotFoundException {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().matchAndBump(revision);
        Player player = game.getPlayers().stream().filter(element -> element.getId() == playerId).findFirst().orElseThrow(() -> new PlayerNotFoundException(playerId));
        game.getPlayers().remove(player);
        gameRepository.save(gameEntityMapper.map(game));
        return game.getRevision();
    }

    @Override
    public Revision shuffle(int gameId, Revision revision) throws GameNotFoundException, RevisionsDontMatch {
        Game game = Optional.of(gameRepository.getGameById(gameId)).map(gameEntityMapper::map).orElseThrow(() -> new GameNotFoundException(gameId));
        game.getRevision().matchAndBump(revision);
        game.getDeck().shuffle();
        gameRepository.save(gameEntityMapper.map(game));
        return game.getRevision();


    }
}
