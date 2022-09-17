package com.example.demo.web.card;

import com.example.demo.domain.deck.Suit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardJson {
    private Suit suit;
    private Integer value;
}
