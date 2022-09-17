package com.example.demo.domain.deck;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    private Suit suit;
    private Integer value;
}
