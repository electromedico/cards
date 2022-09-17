package com.example.demo.domain.player;

import com.example.demo.domain.deck.Card;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class Player {
    Integer id;
    List<Card> cards;
}
