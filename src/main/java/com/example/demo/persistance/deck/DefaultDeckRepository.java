/* (C) 2022 */
package com.example.demo.persistance.deck;

import com.example.demo.domain.deck.Deck;
import com.example.demo.domain.deck.DeckRepository;
import com.example.demo.errors.DeckNotFoundException;
import com.example.demo.errors.NoUnassignedDecksException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class DefaultDeckRepository implements DeckRepository {

  @Autowired private DeckEntityDao deckEntityDao;
  @Autowired private DeckEntityMapper deckEntityMapper;

  @Override
  public Integer createDeck() {
    return deckEntityDao.save(deckEntityMapper.toDto(Deck.buildNewDeck())).getId();
  }

  @Override
  public List<Deck> findDecksByGameId(Integer gameId) {
    return deckEntityDao.findByGameId(gameId).stream().map(deckEntityMapper::fromDto).toList();
  }

  @Override
  public Deck findDeckById(Integer deckId) throws DeckNotFoundException {
    return deckEntityDao
        .findById(deckId)
        .map(deckEntityMapper::fromDto)
        .orElseThrow(() -> new DeckNotFoundException(deckId));
  }

  @Override
  public Deck findFirstUnassigned() throws NoUnassignedDecksException {
    return deckEntityDao
        .findFirstByGameIdNull()
        .map(deckEntityMapper::fromDto)
        .orElseThrow(NoUnassignedDecksException::new);
  }

  @Override
  public void updateDeck(Deck deck) {
    deckEntityDao.save(deckEntityMapper.toDto(deck));
  }

  @Override
  public void updateDecks(List<Deck> decks) {
    deckEntityDao.saveAll(decks.stream().map(deckEntityMapper::toDto).toList());
  }
}
