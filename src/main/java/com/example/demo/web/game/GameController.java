package com.example.demo.web.game;

import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.erros.GameNotFoundException;
import com.example.demo.erros.PlayerNotFoundException;
import com.example.demo.erros.RevisionsDontMatch;
import com.example.demo.utils.Revision;
import com.example.demo.web.player.PlayerJson;
import com.example.demo.web.player.PlayerJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    public GameService gameService;

    @Autowired
    public PlayerJsonMapper playerJsonMapper;

    @GetMapping("/new")
    public Integer newGame(HttpServletResponse response) {
        response.addHeader(HttpHeaders.IF_MATCH, "0");
        return gameService.newGame();
    }

    @DeleteMapping("/${id}")
    public void deleteGame(@PathVariable int id, @RequestHeader(HttpHeaders.IF_MATCH) int revision ) throws GameNotFoundException, RevisionsDontMatch {
        gameService.deleteGame(id, new Revision(revision));
    }

    @GetMapping("/${id}/add-deck")
    public void addDeck(@PathVariable int id, @RequestHeader(HttpHeaders.IF_MATCH) int revision, HttpServletResponse response) throws RevisionsDontMatch, GameNotFoundException {
        Revision newRevision = gameService.addDeck(id, new Revision(revision));
        response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());

    }

    @GetMapping("/${id}/add-player")
    public Integer addPlayer(@PathVariable int id,@RequestHeader(HttpHeaders.IF_MATCH) int revision,  HttpServletResponse response) throws GameNotFoundException, RevisionsDontMatch {
        Game game = gameService.addPlayer(id, new Revision(revision));
        response.addHeader(HttpHeaders.IF_MATCH, game.getRevision().toString());
        return game.getPlayers().get(game.getPlayers().size()-1).getId();
    }

    @GetMapping("/${id}/remove-player/${playerId}")
    public void removePlayer(@PathVariable int id, @PathVariable int playerId, @RequestHeader(HttpHeaders.IF_MATCH) int revision, HttpServletResponse response) throws RevisionsDontMatch, PlayerNotFoundException, GameNotFoundException {
        Revision newRevision = gameService.removePlayer(id, playerId, new Revision(revision));
        response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());
    }

    @GetMapping("/${id}/deal-to-player/${playerId}")
    public PlayerJson dealCardToPlayer( @PathVariable int id, @PathVariable int playerId, @RequestHeader(HttpHeaders.IF_MATCH) int revision, HttpServletResponse response) throws RevisionsDontMatch, PlayerNotFoundException, GameNotFoundException {
        Game game = gameService.dealCardToPlayer(id, playerId, new Revision(revision));
        response.addHeader(HttpHeaders.IF_MATCH, game.getRevision().toString());
        return playerJsonMapper.mapPlayer(game.getPlayers().stream().filter(player -> player.getId()==playerId);
    }

    @GetMapping("/${id}/get-players")
    public List<PlayerJson> getPlayers(@PathVariable int id) throws GameNotFoundException {
        return gameService.getPlayers(id).stream().map(playerJsonMapper::mapPlayer).toList();
    }

    @GetMapping("/${id}/shuffle")
    public void shuffle(@PathVariable int id, @RequestHeader(HttpHeaders.IF_MATCH) int revision, HttpServletResponse response) throws RevisionsDontMatch, GameNotFoundException {
        Revision newRevision = gameService.shuffle(id, new Revision(revision));
        response.addHeader(HttpHeaders.IF_MATCH, newRevision.toString());
    }
}
