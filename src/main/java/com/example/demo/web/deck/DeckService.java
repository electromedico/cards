/* (C) 2022 */
package com.example.demo.web.deck;

import com.example.demo.domain.deck.Deck;
import com.example.demo.errors.DeckNotFoundException;

public interface DeckService {

  Integer createDeck();

  Deck getDeck(Integer id) throws DeckNotFoundException;

  Deck getDeckSorted(Integer id) throws DeckNotFoundException;
}
