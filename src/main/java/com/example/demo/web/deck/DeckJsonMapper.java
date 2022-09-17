package com.example.demo.web.deck;


import com.example.demo.domain.deck.Deck;
import com.example.demo.web.card.CardJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeckJsonMapper {

    @Autowired
    CardJsonMapper cardJsonMapper;
    public DeckJson map(Deck deck){

        return DeckJson.builder().cards(deck.getCards().stream().map(cardJsonMapper::mapCard).toList()).build();

    }
}
