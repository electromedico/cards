package com.example.demo.persistance.game;

import com.example.demo.persistance.player.PlayerEntity;

import javax.persistence.*;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="game")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "revision")
    private Integer revision;

    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerEntity> playerEntities = new ArrayList<>();

    @Lob
    @Column(name = "cards")
    private Clob cards;

    public List<PlayerEntity> getPlayers() {
        return playerEntities;
    }

    public void setPlayers(List<PlayerEntity> playerEntities) {
        this.playerEntities = playerEntities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Clob getCards() {
        return cards;
    }

    public void setCards(Clob cards) {
        this.cards = cards;
    }
}
