/* (C) 2022 */
package com.example.demo.domain.deck;

import com.example.demo.errors.DeckNotFoundException;
import com.example.demo.web.deck.DeckService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckServiceImpl implements DeckService {

  @Autowired DeckRepository deckRepository;

  @Override
  public Integer createDeck() {
    return deckRepository.createDeck();
  }

  @Override
  public Deck getDeck(Integer id) throws DeckNotFoundException {
    return deckRepository.findDeckById(id);
  }

  @Override
  public Deck getDeckSorted(Integer id) throws DeckNotFoundException {
    Deck deck = this.getDeck(id);
    List<Card> hearts = new ArrayList<>();
    List<Card> spades = new ArrayList<>();
    List<Card> clubs = new ArrayList<>();
    List<Card> diamonds = new ArrayList<>();
    deck.getCards()
        .forEach(
            card -> {
              switch (card.getSuit()) {
                case HEART -> hearts.add(card);
                case SPADE -> spades.add(card);
                case CLUB -> clubs.add(card);
                case DIAMOND -> diamonds.add(card);
              }
            });
    List<Card> sortedList =
        new ArrayList<>(
            hearts.stream().sorted(Comparator.comparingInt(Card::getValue).reversed()).toList());
    sortedList.addAll(
        spades.stream().sorted(Comparator.comparingInt(Card::getValue).reversed()).toList());
    sortedList.addAll(
        clubs.stream().sorted(Comparator.comparingInt(Card::getValue).reversed()).toList());
    sortedList.addAll(
        diamonds.stream().sorted(Comparator.comparingInt(Card::getValue).reversed()).toList());

    deck.setCards(sortedList);
    return deck;
  }
}
