package com.example.demo.web.player;

import com.example.demo.domain.deck.Card;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class PlayerJson {
    Integer id;
    List<Card> cards;
}
