package com.example.demo.persistance.game;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.game.Game;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.ServerException;
import com.example.demo.persistance.player.PlayerEntity;
import com.example.demo.persistance.player.PlayerEntityMapper;
import com.example.demo.utils.Revision;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class GameEntityMapper {

    @Autowired
    private PlayerEntityMapper playerEntityMapper;

    public Game fromDto(GameEntity gameEntity) {

        List<Player> players = gameEntity.getPlayers().stream().map(playerEntityMapper::fromDto).toList();

        Deck deck = Deck.builder().cards(mapCards(gameEntity.getCards())).build();

        return Game.builder().id(gameEntity.getId()).deck(deck).players(players).revision(new Revision(gameEntity.getRevision())).build();

    }

    public GameEntity toDto(Game game){
        GameEntity gameEntity = new GameEntity();

        List<PlayerEntity> playerEntities = game.getPlayers().stream().map(playerEntityMapper::toDto).toList();

        gameEntity.setId(game.getId());
        Clob cards;
        try {
            cards = mapCards(game.getDeck().getCards());
        } catch (SQLException e) {
            throw new ServerException(e);
        }
        gameEntity.setCards(cards);
        gameEntity.setPlayers(playerEntities);
        return gameEntity;
    }

    private List<Card> mapCards (Clob clob){
        String json = clob.toString();
        return Arrays.stream(new Gson().fromJson(json, Card[].class)).toList();
    }

    public Clob mapCards(List<Card> cards) throws SQLException {
        String json = new Gson().toJson(cards);
        return new SerialClob(json.toCharArray());
    }
}
