package com.example.demo.persistance.game;

import com.example.demo.persistance.deck.DeckEntity;
import com.example.demo.persistance.player.PlayerEntity;

import javax.persistence.*;
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
    private List<DeckEntity> deckEntities = new ArrayList<>();

    @OneToMany(mappedBy = "gameEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerEntity> playerEntities = new ArrayList<>();

    public List<PlayerEntity> getPlayers() {
        return playerEntities;
    }

    public void setPlayers(List<PlayerEntity> playerEntities) {
        this.playerEntities = playerEntities;
    }

    public List<DeckEntity> getDecks() {
        return deckEntities;
    }

    public void setDecks(List<DeckEntity> deckEntities) {
        this.deckEntities = deckEntities;
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
}
