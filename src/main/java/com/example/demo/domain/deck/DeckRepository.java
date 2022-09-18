/* (C) 2022 */
package com.example.demo.domain.deck;

import com.example.demo.errors.DeckNotFoundException;
import com.example.demo.errors.NoUnassignedDecksException;
import java.util.List;

public interface DeckRepository {

  Integer createDeck();

  List<Deck> findDecksByGameId(Integer gameId);

  Deck findDeckById(Integer deckId) throws DeckNotFoundException;

  Deck findFirstUnassigned() throws NoUnassignedDecksException;

  void updateDeck(Deck deck);

  void updateDecks(List<Deck> decks);
}
