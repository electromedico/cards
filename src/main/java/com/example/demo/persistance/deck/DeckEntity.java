/* (C) 2022 */
package com.example.demo.persistance.deck;

import java.sql.Clob;
import javax.persistence.*;

@Entity
@Table(name = "deck_entity")
public class DeckEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Lob
  @Column(name = "cards")
  private Clob cards;

  @Column(name = "game_id")
  private Integer gameId;

  public Integer getGameId() {
    return gameId;
  }

  public void setGameId(Integer gameId) {
    this.gameId = gameId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Clob getCards() {
    return cards;
  }

  public void setCards(Clob cards) {
    this.cards = cards;
  }
}
