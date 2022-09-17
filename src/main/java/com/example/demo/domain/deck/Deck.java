/* (C) 2022 */
package com.example.demo.domain.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Deck {
  private List<Card> cards;

  public static Deck buildNewDeck() {
    return Deck.builder().cards(buildNewSetOfCards()).build();
  }

  public static List<Card> buildNewSetOfCards() {
    var cards = new ArrayList<Card>();
    for (Suit suit : Suit.values()) {
      for (int i = 0; i < 13; i++) {
        cards.add(new Card(suit, i));
      }
    }
    return cards;
  }

  public void shuffle() {
    List<Card> shuffle = new ArrayList<>();
    List<Card> current = new ArrayList<>(this.cards);
    Random rand = new Random();

    int i = current.size();
    while (i != 0) {
      var next = rand.nextInt(i - 1);
      shuffle.add(current.get(next));
      current.remove(next);
      --i;
    }
    this.setCards(shuffle);
  }
}
