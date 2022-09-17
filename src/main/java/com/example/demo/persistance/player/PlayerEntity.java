package com.example.demo.persistance.player;

import com.example.demo.persistance.game.GameEntity;

import javax.persistence.*;
import java.sql.Clob;

@Entity
@Table(name = "player")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_entity_id")
    private GameEntity gameEntity;

    @Lob
    @Column(name = "cards")
    private Clob cards;

    public Clob getCards() {
        return cards;
    }

    public void setCards(Clob cards) {
        this.cards = cards;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}