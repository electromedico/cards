package com.example.demo.web.game;

import com.example.demo.domain.player.Player;
import com.example.demo.web.player.PlayerJson;
import com.example.demo.web.player.PlayerJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    public GameService gameService;

    @Autowired
    public PlayerJsonMapper playerJsonMapper;

    @GetMapping("/new")
    public int newGame() {
        return gameService.newGame();
    }

    @DeleteMapping("/${id}")
    public void deleteGame(@PathVariable int id) {
        gameService.deleteGame(id);
    }

    @GetMapping("/${id}/add-deck")
    public void addDeck(@PathVariable int id) {
        gameService.addDeck(id);
    }

    @GetMapping("/${id}/add-player/${playerId}")
    public void addPlayer(@PathVariable int id, @PathVariable int playerId) {
        gameService.addPlayer(id, playerId);
    }

    @GetMapping("/${id}/remove-player/${playerId}")
    public void removePlayer(@PathVariable int id, @PathVariable int playerId) {
        gameService.removePlayer(id, playerId);
    }

    @GetMapping("/${id}/deal-to-player/${playerId}")
    public PlayerJson dealCardToPlayer(@PathVariable int id, @PathVariable int playerId) {
        Player player = gameService.dealCardToPlayer(id, playerId);
        return playerJsonMapper.mapPlayer(player);
    }

    @GetMapping("/${id}/get-players")
    public List<PlayerJson> getPlayers(@PathVariable int id) {
        return gameService.getPlayers(id).stream().map(playerJsonMapper::mapPlayer).toList();
    }

    @GetMapping("/${id}/shuffle")
    public void shuffle(@PathVariable int id) {
        gameService.shuffle(id);
    }
}
