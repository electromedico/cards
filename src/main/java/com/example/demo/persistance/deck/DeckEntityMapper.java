package com.example.demo.persistance.deck;

import com.example.demo.domain.deck.Card;
import com.example.demo.domain.deck.Deck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class DeckEntityMapper {

    public Deck map(DeckEntity deckEntity) throws JsonProcessingException {
        List<Card> cards = mapCards(deckEntity.getCards());
        return Deck.builder().id(deckEntity.getId()).cards(cards).build();
    }

    private List<Card> mapCards (Clob clob) throws JsonProcessingException {
        String json = clob.toString();
        return Arrays.stream(new Gson().fromJson(json, Card[].class)).toList();
    }

    public DeckEntity map(Deck deck) throws SQLException {
        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setId(deck.getId());
        String json = new Gson().toJson(deck.getCards());
        deckEntity.setCards(new SerialClob(json.toCharArray()));
        return deckEntity;
    }
}
