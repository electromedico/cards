package com.example.demo.persistance.player;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PlayerEntityMapper {
    public Player map(PlayerEntity playerEntity) throws JsonProcessingException {
        List<Card> cards = mapCards(playerEntity.getCards());
        return Player.builder().id(playerEntity.getId()).cards(cards).build();
    }


    private List<Card> mapCards (Clob clob) throws JsonProcessingException {
        String json = clob.toString();

        List<Card> cards = Arrays.stream(new Gson().fromJson(json, Card[].class)).toList();
        return cards;
    }

    public PlayerEntity map(Player player) throws SQLException {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(player.getId());
        String json = new Gson().toJson(player.getCards());
        playerEntity.setCards(new SerialClob(json.toCharArray()));
        return playerEntity;
    }
}
