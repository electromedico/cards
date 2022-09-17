/* (C) 2022 */
package com.example.demo.persistance.player;

import java.sql.Clob;
import javax.persistence.*;

@Entity
@Table(name = "player")
public class PlayerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "gameId", nullable = false)
  private Integer gameId;

  @Lob
  @Column(name = "cards")
  private Clob cards;

  public Clob getCards() {
    return cards;
  }

  public void setCards(Clob cards) {
    this.cards = cards;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGameId() {
    return gameId;
  }

  public void setGameId(Integer gameId) {
    this.gameId = gameId;
  }
}
