/* (C) 2022 */
package com.example.demo.domain.player;

import com.example.demo.domain.deck.Card;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Player {
  Integer id;
  List<Card> cards;
}
