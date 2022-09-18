/* (C) 2022 */
package com.example.demo.web.deck;

import com.example.demo.domain.deck.Deck;
import com.example.demo.errors.DeckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/deck")
public class DeckController {

  @Autowired DeckService deckService;

  @Autowired DeckJsonMapper deckJsonMapper;

  @GetMapping("/new")
  public Integer newDeck(HttpServletResponse response) {
    response.addHeader(HttpHeaders.IF_MATCH, "0");
    return deckService.createDeck();
  }

  @GetMapping("/{id}/count-cards-left")
  public CardsLeftJson getCount(@PathVariable Integer id) throws DeckNotFoundException {
    return deckJsonMapper.mapCardsLeft(deckService.getDeck(id));
  }

  @GetMapping("/{id}/cards-left-sorted")
  public DeckJson getCardsLeftSorted(@PathVariable Integer id) throws DeckNotFoundException {
    Deck deck = deckService.getDeckSorted(id);
    return deckJsonMapper.mapDeck(deck);
  }
}
