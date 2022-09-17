package com.example.demo.persistance.game;

import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.persistance.deck.DeckEntity;
import com.example.demo.persistance.deck.DeckEntityMapper;
import com.example.demo.persistance.player.PlayerEntity;
import com.example.demo.persistance.player.PlayerEntityMapper;
import com.example.demo.utils.Revision;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class GameEntityMapper {

    @Autowired
    private DeckEntityMapper deckEntityMapper;

    @Autowired
    private PlayerEntityMapper playerEntityMapper;

    public Game map(GameEntity gameEntity){

        List<Deck> decks = gameEntity.getDecks().stream().map((DeckEntity deckEntity) -> {
            try {
                return deckEntityMapper.map(deckEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        List<Player> players = gameEntity.getPlayers().stream().map((PlayerEntity playerEntity) -> {
            try {
                return playerEntityMapper.map(playerEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        return Game.builder().id(gameEntity.getId()).decks(decks).players(players).revision(new Revision(gameEntity.getRevision())).build();

    }

    public GameEntity map(Game game) {
        GameEntity gameEntity = new GameEntity();

        List<PlayerEntity> playerEntities = game.getPlayers().stream().map(player ->
        {
            try {
                return playerEntityMapper.map(player);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        List<DeckEntity> deckEntities = game.getDecks().stream().map(deck -> {
            try {
              return deckEntityMapper.map(deck);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        gameEntity.setId(game.getId());
        gameEntity.setDecks(deckEntities);
        gameEntity.setPlayers(playerEntities);
        return gameEntity;
    }
}
