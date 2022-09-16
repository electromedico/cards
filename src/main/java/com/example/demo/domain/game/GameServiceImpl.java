package com.example.demo.domain.game;

import com.example.demo.domain.player.Player;
import com.example.demo.web.game.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public int newGame() {
        return 0;
    }

    @Override
    public void deleteGame(int id) {

    }

    @Override
    public void shuffle(int id) {

    }

    @Override
    public void addDeck(int id) {

    }

    @Override
    public void addPlayer(int id, int playerId) {

    }

    @Override
    public Player dealCardToPlayer(int id, int playerId) {
        return null;
    }

    @Override
    public List<Player> getPlayers(int id) {
        return null;
    }

    @Override
    public void removePlayer(int id, int playerId) {

    }
}
