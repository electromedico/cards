package com.example.demo.web.deck;

import com.example.demo.web.card.CardJson;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeckJson {
    private List<CardJson> cards;

}
