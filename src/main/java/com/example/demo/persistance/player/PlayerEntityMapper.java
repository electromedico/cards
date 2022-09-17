package com.example.demo.persistance.player;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.player.Player;
import com.example.demo.errors.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class PlayerEntityMapper {
    public Player fromDto(PlayerEntity playerEntity){
        List<Card> cards = null;
        try {
            cards = mapCards(playerEntity.getCards());
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
        return Player.builder().id(playerEntity.getId()).cards(cards).build();
    }

    public PlayerEntity toDto(Player player){
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(player.getId());
        String json = new Gson().toJson(player.getCards());
        try {
            playerEntity.setCards(new SerialClob(json.toCharArray()));
        } catch (SQLException e) {
            throw new ServerException(e);
        }
        return playerEntity;
    }

    private List<Card> mapCards (Clob clob) throws JsonProcessingException {
        String json = clob.toString();

        List<Card> cards = Arrays.stream(new Gson().fromJson(json, Card[].class)).toList();
        return cards;
    }
}
